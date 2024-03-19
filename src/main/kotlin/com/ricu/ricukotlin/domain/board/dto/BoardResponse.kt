package com.ricu.ricukotlin.domain.board.dto

import com.ricu.ricukotlin.domain.board.model.Board

data class BoardResponse(
    val galleryId: String,
    val title: String,
    val content: String,
    val writer: String
)

{
    companion object
    {
        fun from(board: Board): BoardResponse
        {
            return BoardResponse(
                galleryId = board.gallery.galleryUrl,
                title =  board.title,
                content = board.content,
                writer = board.creator?.nickname!!
            )
        }
    }
}