package com.ricu.ricukotlin.domain.comment.dto

import com.ricu.ricukotlin.domain.comment.model.Comment
import java.time.LocalDateTime

data class CommentResponse (
    val commentId: Long?,
    val bno: Long?,
    val commentText: String,
    val writerUsername: String,
    val writerProfileImage: String?,
    val writerNickname: String?,
    val createdTime: LocalDateTime
)
{
    companion object
    {
        fun from(comment: Comment): CommentResponse
                = comment.let {
            CommentResponse(
                commentId = it.id,
                bno = it.board.id,
                commentText = it.commentText,
                writerUsername = it.creator?.username!!,
                writerProfileImage = it.creator?.profileImage?.getLink(),
                writerNickname = it.creator?.nickname,
                createdTime = it.createdAt.toLocalDateTime()
            )
        }
    }
}