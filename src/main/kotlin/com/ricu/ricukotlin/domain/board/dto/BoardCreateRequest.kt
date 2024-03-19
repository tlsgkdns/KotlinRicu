package com.ricu.ricukotlin.domain.board.dto

import com.ricu.ricukotlin.domain.board.model.Board
import com.ricu.ricukotlin.domain.gallery.repository.GalleryRepository
import com.ricu.ricukotlin.global.util.RepositoryUtil

data class BoardCreateRequest(
    val title: String,
    val content: String,
)
{
    fun to(galleryId: String, galleryRepository: GalleryRepository): Board
    {
        return Board(
            title = title,
            gallery = RepositoryUtil.getValidatedEntity(galleryRepository, galleryId),
            content = content
        )
    }
}
