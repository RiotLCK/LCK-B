package com.lckb.lck_backend.domain.dto.post

data class UpdatePostRequest(
    val title: String,
    val content: String,
    val category: String
)
