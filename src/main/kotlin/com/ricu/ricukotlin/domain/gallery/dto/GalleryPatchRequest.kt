package com.ricu.ricukotlin.domain.gallery.dto

data class GalleryPatchRequest(
    val explanation: String?,
    val galleryImageName: String?,
    val popularThreshold: Int?
)
