package com.lckb.lck_backend.repository

import com.lckb.lck_backend.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByNickname(nickname: String): User?
} 