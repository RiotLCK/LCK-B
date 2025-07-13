package com.lckb.lck_backend.config

import com.lckb.lck_backend.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = jwtService.extractTokenFromHeader(authHeader)
            
            if (token != null && jwtService.validateToken(token) && !jwtService.isTokenExpired(token)) {
                val userId = jwtService.getUserIdFromToken(token)
                val email = jwtService.getEmailFromToken(token)
                
                // UserDetails 생성 (간단한 구현)
                val userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password("") // JWT에서는 비밀번호가 필요 없음
                    .authorities("USER")
                    .build()
                
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        
        filterChain.doFilter(request, response)
    }
} 