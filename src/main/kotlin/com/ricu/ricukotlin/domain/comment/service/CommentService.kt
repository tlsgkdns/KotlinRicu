package com.ricu.ricukotlin.domain.comment.service

import com.ricu.ricukotlin.domain.comment.dto.CommentRequest
import com.ricu.ricukotlin.domain.comment.dto.CommentResponse
import com.ricu.ricukotlin.global.common.PageRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import javax.xml.stream.events.Comment

interface CommentService {
    fun writeComment(boardId: Long, commentRequest: CommentRequest): CommentResponse
    fun getComment(commentId: Long): CommentResponse
    fun removeComment(commentId: Long)
    fun modifyCommentText(commentId: Long, commentRequest: CommentRequest): CommentResponse
    fun getComments(boardId: Long, pageRequestDTO: PageRequestDTO): Page<CommentResponse>
}