package com.ricu.ricukotlin.domain.gallery.dto

import com.ricu.ricukotlin.domain.gallery.model.Gallery
import com.ricu.ricukotlin.domain.image.model.Image

data class GalleryCreateRequest(
    val title: String,
    val explanation: String,
    val galleryImageName: String?
)
{
    fun to(): Gallery
    {
        return Gallery(title = title, explanation = explanation, galleryImage = Image.from(galleryImageName))
    }

}