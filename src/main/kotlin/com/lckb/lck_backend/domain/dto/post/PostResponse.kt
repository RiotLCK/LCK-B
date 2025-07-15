package com.lckb.lck_backend.domain.dto.post

import com.lckb.lck_backend.domain.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val author: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(post: Post): PostResponse {
            return PostResponse(
                id = post.id,
                title = post.title,
                content = post.content,
                category = post.category,
                author = post.user.nickname,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt
            )
        }
    }
}
