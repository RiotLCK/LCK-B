package com.lckb.lck_backend.controller

import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.service.CommentLikeService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments/{commentId}/like")
class CommentLikeController(
    private val commentLikeService: CommentLikeService
) {

    // 댓글 좋아요 토글
    @PostMapping
    fun toggleLike(
        @PathVariable commentId: Long,
        @AuthenticationPrincipal(expression = "user") user: User
    ): Map<String, Any> {
        val liked = commentLikeService.toggleLike(commentId, user)
        return mapOf(
            "liked" to liked,
            "message" to if (liked) "좋아요를 눌렀습니다." else "좋아요를 취소했습니다."
        )
    }
}
