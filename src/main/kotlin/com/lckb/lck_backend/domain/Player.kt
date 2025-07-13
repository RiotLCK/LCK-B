package com.lckb.lck_backend.domain

import jakarta.persistence.*

@Entity
@Table(name = "players")
class Player(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 100)
    val name: String,

    @Column(nullable = false, length = 20)
    val position: String,

    @Column(nullable = false, length = 50)
    val team: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    @Column(name = "image_url", columnDefinition = "TEXT")
    val imageUrl: String
) {
    // JPA를 위한 기본 생성자
    constructor() : this(
        id = 0,
        name = "",
        position = "",
        team = "",
        description = "",
        imageUrl = ""
    )
} 