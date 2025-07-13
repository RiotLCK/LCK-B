package com.lckb.lck_backend.domain.dto

data class LoginResponse(
    val result: Boolean = true,
    val message: String,
    val token: String? = null, // Access Token
    val refreshToken: String? = null, // Refresh Token
    val user: UserInfo? = null
) 