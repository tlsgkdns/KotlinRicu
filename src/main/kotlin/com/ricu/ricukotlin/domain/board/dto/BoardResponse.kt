package com.ricu.ricukotlin.domain.board.dto

import com.ricu.ricukotlin.domain.board.model.Board
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class BoardResponse(
    val galleryId: Long,
    val boardId: Long,
    val title: String,
    val content: String,
    val creatorUsername: String,
    val views: Long,
    val likeCount: Int,
    val createdTime: LocalDateTime
)
{
    companion object
    {
        fun from(board: Board): BoardResponse
        {
            return BoardResponse(
                galleryId = board.gallery.galleryId!!,
                boardId = board.id!!,
                title =  board.title,
                content = board.content,
                creatorUsername = board.creator?.username!!,
                views = board.view,
                likeCount = board.likeMembers.size,
                createdTime = board.createdTime.toLocalDateTime()
            )
        }
    }
}