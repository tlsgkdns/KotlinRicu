package com.ricu.ricukotlin.domain.gallery.service

import com.ricu.ricukotlin.domain.gallery.dto.GalleryCreateRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryPatchRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryResponse
import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.common.PageResponseDTO
import com.ricu.ricukotlin.global.common.available.dto.AvailableRequest
import com.ricu.ricukotlin.global.common.available.dto.AvailableResponse

interface GalleryService {
    fun createGallery(galleryCreateRequest: GalleryCreateRequest): GalleryResponse
    fun getGalleryImage(id: Long): String?
    fun editGalleryInfo(id: Long, galleryPatchRequest: GalleryPatchRequest): GalleryResponse
    fun deleteGallery(id: Long): GalleryResponse
    fun getGallery(id: Long): GalleryResponse
    fun searchGallery(keyword: String?, pageRequestDTO: PageRequestDTO): PageResponseDTO<GalleryResponse>
    fun availableGalleryTitle(availableRequest: AvailableRequest): AvailableResponse
}