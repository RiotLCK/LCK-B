package com.lckb.lck_backend.domain.dto.post

data class CreatePostRequest(
    val title: String,
    val content: String,
    val category: String
)