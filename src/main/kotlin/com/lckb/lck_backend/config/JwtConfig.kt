package com.lckb.lck_backend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtConfig {
    lateinit var secret: String
    var accessTokenValidity: Long = 3600000 // 1시간
    var refreshTokenValidity: Long = 86400000 // 24시간
} 