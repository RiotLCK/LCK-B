package com.lckb.lck_backend.domain

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "posts")
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(nullable = false, length = 200)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(nullable = false, length = 50)
    val category: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    // JPA를 위한 기본 생성자
    constructor() : this(
        id = 0,
        user = User(),
        title = "",
        content = "",
        category = "",
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )
} 