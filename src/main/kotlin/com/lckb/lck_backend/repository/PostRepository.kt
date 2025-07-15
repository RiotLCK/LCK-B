package com.lckb.lck_backend.repository

import com.lckb.lck_backend.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByCategory(category: String): List<Post>
}
