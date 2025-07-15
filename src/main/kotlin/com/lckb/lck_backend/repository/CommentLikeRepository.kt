package com.lckb.lck_backend.repository

import com.lckb.lck_backend.domain.Comment
import com.lckb.lck_backend.domain.CommentLike
import com.lckb.lck_backend.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentLikeRepository : JpaRepository<CommentLike, Long> {

    fun findByCommentAndUser(comment: Comment, user: User): CommentLike?

    fun existsByCommentAndUser(comment: Comment, user: User): Boolean

    fun countByComment(comment: Comment): Long
}
