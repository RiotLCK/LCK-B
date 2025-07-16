package com.lckb.lck_backend.service

import com.lckb.lck_backend.domain.Post
import com.lckb.lck_backend.domain.PostLike
import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.repository.PostLikeRepository
import com.lckb.lck_backend.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostLikeService(
    private val postLikeRepository: PostLikeRepository,
    private val postRepository: PostRepository
) {

    // 게시글 좋아요 토글 (좋아요/취소)
    @Transactional
    fun toggleLike(postId: Long, user: User): Boolean {
        val post = postRepository.findById(postId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 게시글입니다.") }

        val existingLike = postLikeRepository.findByPostAndUser(post, user)

        return if (existingLike != null) {
            // 좋아요가 이미 눌러져 있으면 취소
            postLikeRepository.delete(existingLike)
            false
        } else {
            // 좋아요가 없으면 생성
            val newLike = PostLike(post = post, user = user)
            postLikeRepository.save(newLike)
            true
        }
    }

    // 게시글 좋아요 개수 조회
    fun countLikes(post: Post): Long {
        return postLikeRepository.countByPost(post)
    }
}
