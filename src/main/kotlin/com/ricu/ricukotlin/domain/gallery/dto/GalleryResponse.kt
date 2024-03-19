package com.ricu.ricukotlin.domain.gallery.dto

import com.ricu.ricukotlin.domain.gallery.model.Gallery
import jdk.jfr.Threshold

data class GalleryResponse(
    val galleryUrl: String,
    val title: String,
    val explanation: String,
    val galleryImageName: String?,
    val popularThreshold: Int,
    val creatorName: String
)
{
    companion object
    {
        fun from(gallery: Gallery): GalleryResponse
        {
            return gallery.let {
                GalleryResponse(
                    galleryUrl = it.galleryUrl,
                    title = it.title,
                    explanation = it.explanation,
                    galleryImageName = it.galleryImage?.uuid,
                    popularThreshold = it.popularThreshold,
                    creatorName = it.creator?.nickname!!
                )
            }
        }
    }
}