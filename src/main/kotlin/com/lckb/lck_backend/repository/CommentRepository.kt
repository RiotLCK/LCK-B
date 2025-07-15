package com.lckb.lck_backend.repository

import com.lckb.lck_backend.domain.Comment
import com.lckb.lck_backend.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByPost(post: Post): List<Comment>
}
