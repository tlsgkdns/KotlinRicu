package com.ricu.ricukotlin.domain.comment.dto

import com.ricu.ricukotlin.domain.comment.model.Comment

data class CommentResponse (
    val commentId: Long?,
    val bno: Long?,
    val commentText: String,
    val writerId: String?,
    val writerProfileImage: String?,
    val writerUsername: String?
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
                writerId = it.creator?.username,
                writerProfileImage = it.creator?.profileImage?.uuid,
                writerUsername = it.creator?.username
            )
        }
    }
}