package com.lckb.lck_backend.controller

import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.service.PostLikeService
import com.lckb.lck_backend.config.CustomUserDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts/{postId}/like")
class PostLikeController(
    private val postLikeService: PostLikeService
) {

    // 게시글 좋아요 토글
    @PostMapping
    fun toggleLike(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): Map<String, Any> {
        val user = userDetails.user
        val liked = postLikeService.toggleLike(postId, user)
        return mapOf(
            "liked" to liked,
            "message" to if (liked) "좋아요를 눌렀습니다." else "좋아요를 취소했습니다."
        )
    }
}
