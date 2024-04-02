package com.ricu.ricukotlin.domain.gallery.dto

import com.ricu.ricukotlin.domain.gallery.model.Gallery
import jdk.jfr.Threshold

data class GalleryResponse(
    val galleryId: Long,
    val title: String,
    val explanation: String,
    val galleryImageName: String?,
    val popularThreshold: Int,
    val creatorUsername: String?
)
{
    companion object
    {
        fun from(gallery: Gallery): GalleryResponse
        {
            return gallery.let {
                GalleryResponse(
                    title = it.title,
                    explanation = it.explanation,
                    galleryImageName = it.galleryImage?.getLink(),
                    popularThreshold = it.popularThreshold,
                    creatorUsername = it.creator?.username,
                    galleryId = it.galleryId!!
                )
            }
        }
    }
}