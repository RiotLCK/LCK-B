package com.lckb.lck_backend.service

import com.lckb.lck_backend.domain.Comment
import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.domain.dto.comment.CommentResponse
import com.lckb.lck_backend.domain.dto.comment.CreateCommentRequest
import com.lckb.lck_backend.repository.CommentRepository
import com.lckb.lck_backend.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val commentLikeService: CommentLikeService
) {

    // 댓글 생성
    fun createComment(request: CreateCommentRequest, user: User): Comment {
        val post = postRepository.findById(request.postId).orElseThrow {
            IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        }

        val comment = Comment(
            post = post,
            user = user,
            content = request.content
        )
        return commentRepository.save(comment)
    }

    // 댓글 목록 (엔티티 리스트 반환)
    fun getCommentsByPost(postId: Long): List<Comment> {
        val post = postRepository.findById(postId).orElseThrow {
            IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        }
        return commentRepository.findAllByPost(post)
    }

    // 댓글 목록 (응답 DTO 반환 + 좋아요 정보 포함)
    fun getCommentResponsesByPost(postId: Long, user: User?): List<CommentResponse> {
        val post = postRepository.findById(postId).orElseThrow {
            IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        }

        val comments = commentRepository.findAllByPost(post)

        return comments.map { comment ->
            val likeCount = commentLikeService.countLikes(comment)
            val likedByUser = if (user != null) {
                commentLikeService.isLikedByUser(comment, user)
            } else {
                false // 비회원은 좋아요 누른 상태일 수 없으므로 false
            }
            CommentResponse.from(comment, likeCount, likedByUser)
        }
    }


    // 댓글 삭제
    @Transactional
    fun deleteComment(id: Long, user: User) {
        val comment = commentRepository.findById(id).orElseThrow {
            IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        }

        if (comment.user.id != user.id) {
            throw IllegalAccessException("댓글 삭제 권한이 없습니다.")
        }

        commentRepository.delete(comment)
    }
}
