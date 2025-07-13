package com.lckb.lck_backend.service

import com.lckb.lck_backend.config.JwtConfig
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
    private val jwtConfig: JwtConfig
) {

    private val secretKey = Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray())

    // Access Token 생성
    fun generateAccessToken(userId: Long, email: String): String {
        return generateToken(userId, email, jwtConfig.accessTokenValidity)
    }

    // Refresh Token 생성
    fun generateRefreshToken(userId: Long, email: String): String {
        return generateToken(userId, email, jwtConfig.refreshTokenValidity)
    }

    // 토큰 생성 공통 메서드
    private fun generateToken(userId: Long, email: String, validity: Long): String {
        val now = Date()
        val expiration = Date(now.time + validity)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("email", email)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    // 토큰에서 사용자 ID 추출
    fun getUserIdFromToken(token: String): Long {
        val claims = getClaimsFromToken(token)
        return claims.subject.toLong()
    }

    // 토큰에서 이메일 추출
    fun getEmailFromToken(token: String): String {
        val claims = getClaimsFromToken(token)
        return claims["email"] as String
    }

    // 토큰 유효성 검증
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: JwtException) {
            false
        }
    }

    // 토큰 만료 확인
    fun isTokenExpired(token: String): Boolean {
        val claims = getClaimsFromToken(token)
        return claims.expiration.before(Date())
    }

    // 토큰에서 Claims 추출
    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    // Authorization 헤더에서 토큰 추출
    fun extractTokenFromHeader(authorizationHeader: String?): String? {
        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader.substring(7)
        } else {
            null
        }
    }
} 