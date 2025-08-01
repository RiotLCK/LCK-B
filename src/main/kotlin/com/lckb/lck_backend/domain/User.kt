package com.lckb.lck_backend.domain

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 100)
    val email: String,

    @Column(nullable = false, length = 255)
    val password: String,

    @Column(nullable = false, length = 50)
    val nickname: String,

    @Column(name = "profile_img")
    val profileImg: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false, length = 50)
    val type: String
) {
    // JPA를 위한 기본 생성자
    constructor() : this(
        id = 0,
        email = "",
        password = "",
        nickname = "",
        profileImg = null,
        createdAt = LocalDateTime.now(),
        type = ""
    )
} 