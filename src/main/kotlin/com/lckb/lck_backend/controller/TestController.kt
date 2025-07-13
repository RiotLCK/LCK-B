package com.lckb.lck_backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/test")
class TestController {

    @GetMapping("/protected")
    fun protectedEndpoint(): ResponseEntity<Map<String, Any>> {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        val email = authentication?.name ?: "Unknown"
        
        return ResponseEntity.ok(mapOf(
            "result" to true,
            "message" to "보호된 API에 접근했습니다.",
            "user" to email,
            "authenticated" to (authentication?.isAuthenticated ?: false)
        ))
    }

    @GetMapping("/public")
    fun publicEndpoint(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "result" to true,
            "message" to "공개 API입니다."
        ))
    }
} 