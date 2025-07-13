package com.lckb.lck_backend.domain.dto

data class TokenRefreshResponse(
    val result: Boolean = true,
    val message: String,
    val accessToken: String? = null,
    val refreshToken: String? = null
) 