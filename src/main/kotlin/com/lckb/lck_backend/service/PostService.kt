package com.lckb.lck_backend.service

import com.lckb.lck_backend.domain.Post
import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.domain.dto.post.CreatePostRequest
import com.lckb.lck_backend.domain.dto.post.UpdatePostRequest
import com.lckb.lck_backend.repository.PostRepository
import java.time.LocalDateTime
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {

    // 게시글 생성
    fun createPost(request: CreatePostRequest, user: User): Post {
        val post = Post(
            user = user,
            title = request.title,
            content = request.content,
            category = request.category
        )
        return postRepository.save(post)
    }

    // 전체 게시글 조회
    fun getAllPosts(): List<Post> = postRepository.findAll()

    // 게시글 단건 조회
    fun getPost(id: Long): Post = postRepository.findById(id).orElseThrow {
        IllegalArgumentException("해당 게시글이 존재하지 않습니다. ID: $id")
    }

    // 게시글 수정 (작성자만 가능)
    @Transactional
    fun updatePost(id: Long, request: UpdatePostRequest, user: User): Post {
        val post = getPost(id)
        if (post.user.id != user.id) {
            throw IllegalAccessException("게시글 수정 권한이 없습니다.")
        }

        // 필드 수동 업데이트
        post.title = request.title
        post.content = request.content
        post.category = request.category
        post.updatedAt = LocalDateTime.now()

        return post
    }


    // 게시글 삭제 (작성자만 가능)
    fun deletePost(id: Long, user: User) {
        val post = getPost(id)
        if (post.user.id != user.id) {
            throw IllegalAccessException("게시글 삭제 권한이 없습니다.")
        }
        postRepository.delete(post)
    }

    // 카테고리별 게시글 조회
    fun getPostsByCategory(category: String): List<Post> = postRepository.findAllByCategory(category)
}
