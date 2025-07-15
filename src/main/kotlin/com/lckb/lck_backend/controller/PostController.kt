package com.lckb.lck_backend.controller

import com.lckb.lck_backend.domain.dto.post.CreatePostRequest
import com.lckb.lck_backend.domain.dto.post.UpdatePostRequest
import com.lckb.lck_backend.domain.dto.post.PostResponse
import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.service.PostService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {

    // 게시글 생성
    @PostMapping
    fun createPost(
        @RequestBody request: CreatePostRequest,
        @AuthenticationPrincipal user: User // Spring Security 인증된 유저
    ): PostResponse {
        val post = postService.createPost(request, user)
        return PostResponse.from(post)
    }

    // 게시글 전체 조회
    @GetMapping
    fun getAllPosts(): List<PostResponse> {
        return postService.getAllPosts().map { PostResponse.from(it) }
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): PostResponse {
        return PostResponse.from(postService.getPost(id))
    }

    // 게시글 수정 (본인만 가능)
    @PutMapping("/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody request: UpdatePostRequest,
        @AuthenticationPrincipal user: User
    ): PostResponse {
        val updatedPost = postService.updatePost(id, request, user)
        return PostResponse.from(updatedPost)
    }

    // 게시글 삭제 (본인만 가능)
    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: User
    ) {
        postService.deletePost(id, user)
    }
}
