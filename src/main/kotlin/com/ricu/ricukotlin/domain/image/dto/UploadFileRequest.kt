package com.ricu.ricukotlin.domain.image.dto

import org.springframework.web.multipart.MultipartFile

data class UploadFileRequest(
    val files: MultipartFile?
)