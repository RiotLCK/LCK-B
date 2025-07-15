package com.lckb.lck_backend.domain.dto.commentlike

data class LikeCommentResponse(
    val commentId: Long,
    val liked: Boolean,        // true: 좋아요 추가됨 / false: 좋아요 취소됨
    val totalLikes: Long       // 현재 해당 댓글의 총 좋아요 수
)
