package com.lckb.lck_backend.controller

import com.lckb.lck_backend.domain.dto.comment.CreateCommentRequest
import com.lckb.lck_backend.domain.dto.comment.CommentResponse
import com.lckb.lck_backend.domain.User
import com.lckb.lck_backend.service.CommentService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentController(
    private val commentService: CommentService
) {

    // 댓글 작성
    @PostMapping
    fun createComment(
        @RequestBody request: CreateCommentRequest,
        @AuthenticationPrincipal(expression = "user") user: User
    ): CommentResponse {
        val comment = commentService.createComment(request, user)
        return CommentResponse.from(comment, likeCount = 0, likedByCurrentUser = false)
    }

    // 게시글에 달린 댓글 목록 조회 (좋아요 수 + 사용자 좋아요 여부 포함)
    @GetMapping("/post/{postId}")
    fun getCommentsByPost(
        @PathVariable postId: Long,
        @AuthenticationPrincipal(expression = "user") user: User?
    ): List<CommentResponse> {
        return commentService.getCommentResponsesByPost(postId, user)
    }


    // 댓글 삭제
    @DeleteMapping("/{id}")
    fun deleteComment(
        @PathVariable id: Long,
        @AuthenticationPrincipal(expression = "user") user: User
    ) {
        commentService.deleteComment(id, user)
    }
}
