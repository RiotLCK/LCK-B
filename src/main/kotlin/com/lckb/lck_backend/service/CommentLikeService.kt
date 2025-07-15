package com.lckb.lck_backend.service

import com.lckb.lck_backend.domain.Comment
import com.lckb.lck_backend.domain.CommentLike
import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.repository.CommentLikeRepository
import com.lckb.lck_backend.repository.CommentRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CommentLikeService(
    private val commentRepository: CommentRepository,
    private val commentLikeRepository: CommentLikeRepository
) {

    // 좋아요 토글
    @Transactional
    fun toggleLike(commentId: Long, user: User): Boolean {
        val comment = commentRepository.findById(commentId).orElseThrow {
            IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        }

        val existing = commentLikeRepository.findByCommentAndUser(comment, user)
        return if (existing != null) {
            commentLikeRepository.delete(existing)
            false // 좋아요 취소됨
        } else {
            val like = CommentLike(comment = comment, user = user)
            commentLikeRepository.save(like)
            true // 좋아요 추가됨
        }
    }

    // 좋아요 개수 반환
    fun countLikes(comment: Comment): Long {
        return commentLikeRepository.countByComment(comment)
    }

    // 특정 유저가 좋아요 눌렀는지 확인
    fun isLikedByUser(comment: Comment, user: User): Boolean {
        return commentLikeRepository.existsByCommentAndUser(comment, user)
    }
}
