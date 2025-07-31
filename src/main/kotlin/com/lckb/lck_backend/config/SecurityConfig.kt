package com.lckb.lck_backend.config

import com.lckb.lck_backend.service.JwtService
import com.lckb.lck_backend.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.http.HttpMethod


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userRepository: UserRepository
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtAuthenticationFilter(jwtService: JwtService): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwtService, userRepository)
    }

    @Bean
    fun filterChain(http: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests { auth ->
                auth
                    // 회원가입, 로그인 등 공개 API
                    .requestMatchers("/api/users/signup", "/api/users/login", "/api/users/check-email", "/api/users/check-nickname", "/api/test/public").permitAll()
                    // 게시글 조회(GET) 및 댓글 조회(GET)은 모두 허용
                    .requestMatchers(HttpMethod.GET, "/api/posts", "/api/posts/*", "/api/comments", "/api/comments/*", "/api/posts/*/comments").permitAll()
                    // 게시글 생성(POST), 수정(PUT), 삭제(DELETE)은 인증 필요
                    .requestMatchers(HttpMethod.POST, "/api/posts", "/api/comments").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/posts/*", "/api/comments/*").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/posts/*", "/api/comments/*").authenticated()
                    // 기타 모든 요청은 인증 필요
                    .anyRequest().authenticated()
            }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:3001")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
} 