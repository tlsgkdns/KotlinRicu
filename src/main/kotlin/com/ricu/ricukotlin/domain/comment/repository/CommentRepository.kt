package com.ricu.ricukotlin.domain.comment.repository

import com.ricu.ricukotlin.domain.comment.model.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {
    fun getCommentsByBoardId(boardId: Long, pageable: Pageable): Page<Comment>
}