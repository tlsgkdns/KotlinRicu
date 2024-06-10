package com.ricu.ricukotlin.domain.gallery.dto

data class GalleryEditRequest(
    val explanation: String?,
    val galleryImageName: String?,
    val popularThreshold: Int?
)
