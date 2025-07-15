package com.lckb.lck_backend.controller

import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.service.CommentLikeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments/{commentId}/like")
class CommentLikeController(
    private val commentLikeService: CommentLikeService
) {

    // 댓글 좋아요 토글
    @PostMapping
    fun toggleLike(
        @PathVariable commentId: Long
    ): Map<String, Any> {
        // 💡 실제로는 인증된 사용자 정보에서 가져와야 함
        val dummyUser = User(id = 1, email = "test@test.com", password = "", nickname = "test", type = "local")

        val liked = commentLikeService.toggleLike(commentId, dummyUser)
        return mapOf(
            "liked" to liked,
            "message" to if (liked) "좋아요를 눌렀습니다." else "좋아요를 취소했습니다."
        )
    }
}
