package com.ricu.ricukotlin.domain.comment.api

import com.ricu.ricukotlin.domain.comment.dto.CommentRequest
import com.ricu.ricukotlin.domain.comment.dto.CommentResponse
import com.ricu.ricukotlin.domain.comment.service.CommentService
import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.common.PageResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("galleries/{galleryId}/boards/{boardId}/comments")
class CommentRestController(
    private val commentService: CommentService
) {

    @GetMapping("/{commentId}")
    fun getComment(@PathVariable commentId: Long):
            ResponseEntity<CommentResponse>
    {
        return commentService.getComment(commentId).let { ResponseEntity.status(HttpStatus.FOUND).body(it) }
    }

    @PostMapping()
    fun writeComment(@PathVariable boardId: Long, @RequestBody createCommentRequest: CommentRequest):
        ResponseEntity<CommentResponse>
    {
        return commentService.writeComment(boardId, createCommentRequest).let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @PatchMapping("/{commentId}")
    fun editComment(@PathVariable commentId: Long, @RequestBody editCommentRequest: CommentRequest): ResponseEntity<CommentResponse>
    {
        return commentService.editCommentText(commentId, editCommentRequest).let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }
    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long): ResponseEntity<Long>
    {
        return commentService.removeComment(commentId).let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }
    }

    @GetMapping()
    fun getComments(@PathVariable boardId: Long, pageRequestDTO: PageRequestDTO): ResponseEntity<PageResponseDTO<CommentResponse>>
    {
        return commentService.getComments(boardId, pageRequestDTO).let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }
    @GetMapping("/count", params = [])
    fun getCommentCount(@PathVariable boardId: Long): ResponseEntity<Int>
    {
        return commentService.getCommentCount(boardId).let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }
}