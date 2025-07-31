package com.lckb.lck_backend.config

import com.lckb.lck_backend.service.JwtService
import com.lckb.lck_backend.repository.UserRepository
import com.lckb.lck_backend.config.CustomUserDetails
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
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

                // 🔥 유저 정보 DB에서 조회
                val user = userRepository.findById(userId).orElse(null)

                if (user != null) {
                    // ✅ CustomUserDetails로 wrapping
                    val userDetails = CustomUserDetails(user)

                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )

                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
