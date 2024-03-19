package com.ricu.ricukotlin.domain.gallery.service

import com.ricu.ricukotlin.domain.gallery.dto.GalleryCreateRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryPatchRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryResponse
import com.ricu.ricukotlin.domain.gallery.dto.GallerySearchKeywordDTO
import com.ricu.ricukotlin.global.common.PageRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GalleryService {
    fun createGallery(galleryCreateRequest: GalleryCreateRequest): GalleryResponse
    fun getGalleryImage(id: String): String?
    fun editGalleryInfo(id: String, galleryPatchRequest: GalleryPatchRequest): GalleryResponse
    fun deleteGallery(id: String)
    fun getGallery(galleryUrl: String): GalleryResponse
    fun searchGallery(keywordDTO: GallerySearchKeywordDTO, pageRequestDTO: PageRequestDTO): Page<GalleryResponse>
}