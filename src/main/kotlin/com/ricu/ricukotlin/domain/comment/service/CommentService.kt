package com.ricu.ricukotlin.domain.comment.service

import com.ricu.ricukotlin.domain.comment.dto.CommentRequest
import com.ricu.ricukotlin.domain.comment.dto.CommentResponse
import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.common.PageResponseDTO

interface CommentService {
    fun writeComment(boardId: Long, commentRequest: CommentRequest): CommentResponse
    fun getComment(commentId: Long): CommentResponse
    fun removeComment(commentId: Long)
    fun editCommentText(commentId: Long, commentRequest: CommentRequest): CommentResponse
    fun getComments(boardId: Long, pageRequestDTO: PageRequestDTO): PageResponseDTO<CommentResponse>
    fun getCommentCount(boardId: Long): Int
}