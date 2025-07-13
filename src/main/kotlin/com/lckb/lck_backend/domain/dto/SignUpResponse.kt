package com.lckb.lck_backend.domain.dto

data class SignUpResponse(
    val result: Boolean = true,
    val message: String,
    val userId: String? = null
) 