package com.lckb.lck_backend.domain

import jakarta.persistence.*

@Entity
@Table(
    name = "comment_likes",
    uniqueConstraints = [UniqueConstraint(columnNames = ["comment_id", "user_id"])]
)
class CommentLike(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    val comment: Comment,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
) {
    // JPA 기본 생성자
    constructor() : this(
        id = 0,
        comment = Comment(
            post = Post(),
            user = User(),
            content = ""
        ),
        user = User()
    )
}
