package com.ricu.ricukotlin.domain.image.dto

data class UploadResponse(
    val uuid: String,
    val filename: String? = "default"
)
{
    fun getLink(): String
    {
        return uuid + "_" + filename
    }
}
