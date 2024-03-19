package com.ricu.ricukotlin.domain.gallery.service

import com.ricu.ricukotlin.domain.gallery.dto.GalleryCreateRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryPatchRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryResponse
import com.ricu.ricukotlin.domain.gallery.dto.GallerySearchKeywordDTO
import com.ricu.ricukotlin.domain.gallery.repository.GalleryRepository
import com.ricu.ricukotlin.domain.image.model.Image
import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.util.RepositoryUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GalleryServiceImpl(
    private val galleryRepository: GalleryRepository
): GalleryService {
    @Transactional
    override fun createGallery(galleryCreateRequest: GalleryCreateRequest): GalleryResponse {
        return galleryCreateRequest.to().let { galleryRepository.save(it) }.let { GalleryResponse.from(it) }
    }
    override fun getGalleryImage(id: String): String? {
        return RepositoryUtil.getValidatedEntity(galleryRepository, id).galleryImage?.getLink()
    }
    @Transactional
    override fun editGalleryInfo(id: String, galleryPatchRequest: GalleryPatchRequest): GalleryResponse {
        return RepositoryUtil.getValidatedEntityWithAuthority(galleryRepository, id)
            .apply { this.explanation = galleryPatchRequest.explanation ?: this.explanation}
            .apply { galleryPatchRequest.galleryImageName?.let { name -> this.galleryImage = Image.from(name) }}
            .apply { this.popularThreshold = galleryPatchRequest.popularThreshold ?: this.popularThreshold }
            .let { gallery -> galleryRepository.save(gallery).let { GalleryResponse.from(it)} }
    }
    @Transactional
    override fun deleteGallery(id: String) {
        val gallery = RepositoryUtil.getValidatedEntityWithAuthority(galleryRepository, id)
        galleryRepository.delete(gallery)
    }

    override fun getGallery(galleryUrl: String): GalleryResponse {
        return RepositoryUtil.getValidatedEntity(galleryRepository, galleryUrl).let { GalleryResponse.from(it) }
    }

    override fun searchGallery(keywordDTO: GallerySearchKeywordDTO, pageRequestDTO: PageRequestDTO): Page<GalleryResponse> {
        return galleryRepository.searchGallery(keywordDTO, pageRequestDTO.getPageable()).map { GalleryResponse.from(it) }
    }
}