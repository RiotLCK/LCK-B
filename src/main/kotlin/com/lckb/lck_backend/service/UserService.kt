package com.lckb.lck_backend.service

import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.domain.dto.*
import com.lckb.lck_backend.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    // 회원가입 처리
    @Transactional
    fun signUp(request: SignUpRequest): SignUpResponse {
        // 이메일 중복 체크
        if (userRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }

        // 닉네임 중복 체크
        if (userRepository.findByNickname(request.nickname) != null) {
            throw IllegalArgumentException("이미 존재하는 닉네임입니다.")
        }

        // 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(request.password)

        // 사용자 생성
        val user = User(
            email = request.email,
            password = encodedPassword,
            nickname = request.nickname,
            type = request.type
        )

        val savedUser = userRepository.save(user)

        return SignUpResponse(
            result = true,
            message = "회원가입이 완료되었습니다.",
            userId = savedUser.id.toString()
        )
    }

    // 로그인 처리 및 JWT 발급
    fun login(request: LoginRequest): LoginResponse {
        // 이메일로 사용자 찾기
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("존재하지 않는 이메일입니다.")

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

        // JWT 토큰 생성
        val accessToken = jwtService.generateAccessToken(user.id, user.email)
        val refreshToken = jwtService.generateRefreshToken(user.id, user.email)

        return LoginResponse(
            result = true,
            message = "로그인이 완료되었습니다.",
            token = accessToken,
            refreshToken = refreshToken,
            user = UserInfo(
                id = user.id,
                email = user.email,
                nickname = user.nickname
            )
        )
    }

    // 토큰 갱신 처리
    fun refreshToken(request: TokenRefreshRequest): TokenRefreshResponse {
        val refreshToken = request.refreshToken
        
        // Refresh Token 유효성 검증
        if (!jwtService.validateToken(refreshToken) || jwtService.isTokenExpired(refreshToken)) {
            throw IllegalArgumentException("유효하지 않은 Refresh Token입니다.")
        }

        // Refresh Token에서 사용자 정보 추출
        val userId = jwtService.getUserIdFromToken(refreshToken)

        // 사용자 존재 확인
        val user = userRepository.findById(userId).orElse(null)
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다.")

        // 새로운 토큰 생성
        val newAccessToken = jwtService.generateAccessToken(user.id, user.email)
        val newRefreshToken = jwtService.generateRefreshToken(user.id, user.email)

        return TokenRefreshResponse(
            result = true,
            message = "토큰이 갱신되었습니다.",
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    // 이메일로 사용자 조회
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    // 이메일 존재 여부 체크
    fun existsByEmail(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }

    // 닉네임 존재 여부 체크
    fun existsByNickname(nickname: String): Boolean {
        return userRepository.findByNickname(nickname) != null
    }

    // Spring Security에서 사용자를 조회할 때 호출
    fun loadUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("해당 이메일로 등록된 사용자를 찾을 수 없습니다.")
    }

    // 로그인한 사용자의 내 정보 조회
    fun getMyInfo(user: User): UserInfo {
        return UserInfo(
            id = user.id,
            email = user.email,
            nickname = user.nickname
        )
    }

} 