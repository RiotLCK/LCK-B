package com.lckb.lck_backend.domain.dto

data class SignUpRequest(
    val email: String,
    val password: String,
    val nickname: String,
    val type: String = "EMAIL" // 기본값은 EMAIL, OAuth는 KAKAO, NAVER 등
) 