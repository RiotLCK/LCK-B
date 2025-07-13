package com.lckb.lck_backend.domain

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "comments")
class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    // JPA를 위한 기본 생성자
    constructor() : this(
        id = 0,
        post = Post(),
        user = User(),
        content = "",
        createdAt = LocalDateTime.now()
    )
} 