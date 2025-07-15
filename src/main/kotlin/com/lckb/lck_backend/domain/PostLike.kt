package com.lckb.lck_backend.domain

import jakarta.persistence.*

@Entity
@Table(
    name = "post_likes",
    uniqueConstraints = [UniqueConstraint(columnNames = ["post_id", "user_id"])]
)
class PostLike(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
) {
    // JPA 기본 생성자
    constructor() : this(
        id = 0,
        post = Post(),
        user = User()
    )
}
