package com.lckb.lck_backend.controller

import com.lckb.lck_backend.domain.dto.SignUpRequest
import com.lckb.lck_backend.domain.dto.SignUpResponse
import com.lckb.lck_backend.domain.dto.LoginRequest
import com.lckb.lck_backend.domain.dto.LoginResponse
import com.lckb.lck_backend.domain.dto.TokenRefreshRequest
import com.lckb.lck_backend.domain.dto.TokenRefreshResponse
import com.lckb.lck_backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<SignUpResponse> {
        return try {
            val response = userService.signUp(request)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(
                SignUpResponse(
                    result = false,
                    message = e.message ?: "회원가입에 실패했습니다."
                )
            )
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        return try {
            val response = userService.login(request)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(
                LoginResponse(
                    result = false,
                    message = e.message ?: "로그인에 실패했습니다."
                )
            )
        }
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: TokenRefreshRequest): ResponseEntity<TokenRefreshResponse> {
        return try {
            val response = userService.refreshToken(request)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(
                TokenRefreshResponse(
                    result = false,
                    message = e.message ?: "토큰 갱신에 실패했습니다."
                )
            )
        }
    }

    @GetMapping("/check-email")
    fun checkEmail(@RequestParam email: String): ResponseEntity<Map<String, Any>> {
        val exists = userService.existsByEmail(email)
        return ResponseEntity.ok(mapOf(
            "result" to true,
            "message" to if (!exists) "사용 가능한 이메일입니다." else "이미 사용 중인 이메일입니다.",
            "available" to !exists
        ))
    }

    @GetMapping("/check-nickname")
    fun checkNickname(@RequestParam nickname: String): ResponseEntity<Map<String, Any>> {
        val exists = userService.existsByNickname(nickname)
        return ResponseEntity.ok(mapOf(
            "result" to true,
            "message" to if (!exists) "사용 가능한 닉네임입니다." else "이미 사용 중인 닉네임입니다.",
            "available" to !exists
        ))
    }
} 