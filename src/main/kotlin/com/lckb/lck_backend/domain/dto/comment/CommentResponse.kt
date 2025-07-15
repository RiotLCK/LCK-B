package com.lckb.lck_backend.domain.dto.comment

import com.lckb.lck_backend.domain.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val author: String,
    val createdAt: LocalDateTime,
    val likeCount: Long,
    val likedByCurrentUser: Boolean
) {
    companion object {
        fun from(
            comment: Comment,
            likeCount: Long,
            likedByCurrentUser: Boolean
        ): CommentResponse {
            return CommentResponse(
                id = comment.id,
                content = comment.content,
                author = comment.user.nickname,
                createdAt = comment.createdAt,
                likeCount = likeCount,
                likedByCurrentUser = likedByCurrentUser
            )
        }
    }
}
