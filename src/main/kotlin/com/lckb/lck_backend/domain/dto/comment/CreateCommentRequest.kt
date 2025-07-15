package com.lckb.lck_backend.domain.dto.comment

data class CreateCommentRequest(
    val postId: Long,
    val content: String
)