package com.lckb.lck_backend.repository

import com.lckb.lck_backend.domain.Post
import com.lckb.lck_backend.domain.PostLike
import com.lckb.lck_backend.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface PostLikeRepository : JpaRepository<PostLike, Long> {
    fun findByPostAndUser(post: Post, user: User): PostLike?
    fun existsByPostAndUser(post: Post, user: User): Boolean
    fun deleteByPostAndUser(post: Post, user: User)
    fun countByPost(post: Post): Long

}
