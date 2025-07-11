package com.lckb.lck_backend.domain

import javax.persistence.*

@Entity
@Table(name = "players")
data class Player(
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
) 