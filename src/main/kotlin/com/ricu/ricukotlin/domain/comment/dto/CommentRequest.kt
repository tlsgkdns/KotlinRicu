package com.ricu.ricukotlin.domain.comment.dto

import com.ricu.ricukotlin.domain.board.repository.BoardRepository
import com.ricu.ricukotlin.domain.comment.model.Comment
import com.ricu.ricukotlin.global.util.RepositoryUtil

data class CommentRequest(
    val commentText: String,
)
{
    fun to(boardRepository: BoardRepository, boardNumber: Long): Comment
    {
        return Comment(commentText = commentText, RepositoryUtil.getValidatedEntity(boardRepository, boardNumber))
    }
}