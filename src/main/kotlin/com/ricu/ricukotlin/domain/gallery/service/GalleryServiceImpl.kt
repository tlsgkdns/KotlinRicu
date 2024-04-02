package com.ricu.ricukotlin.domain.gallery.service

import com.ricu.ricukotlin.domain.gallery.dto.GalleryCreateRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryPatchRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryResponse
import com.ricu.ricukotlin.domain.gallery.repository.GalleryRepository
import com.ricu.ricukotlin.domain.image.model.Image
import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.common.PageResponseDTO
import com.ricu.ricukotlin.global.common.available.AvailableCheckList
import com.ricu.ricukotlin.global.common.available.checker.DuplicateChecker
import com.ricu.ricukotlin.global.common.available.checker.LengthChecker
import com.ricu.ricukotlin.global.common.available.checker.RegexChecker
import com.ricu.ricukotlin.global.common.available.dto.AvailableRequest
import com.ricu.ricukotlin.global.common.available.dto.AvailableResponse
import com.ricu.ricukotlin.global.util.RepositoryUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GalleryServiceImpl(
    private val galleryRepository: GalleryRepository
): GalleryService {

    private val availableTitleCheckList = AvailableCheckList(listOf(
        DuplicateChecker(galleryRepository::existsByTitle),
        RegexChecker(regex = Regex("^[a-zA-Z|s]*$"), errorMessage = "영어만 사용할 수 있습니다."),
        LengthChecker(3, 30)
    ))
    @Transactional
    override fun createGallery(galleryCreateRequest: GalleryCreateRequest): GalleryResponse {
        val createdGallery = galleryCreateRequest.to().let { galleryRepository.save(it) }
        return GalleryResponse.from(createdGallery)
    }
    override fun getGalleryImage(id: Long): String? {
        return RepositoryUtil.getValidatedEntity(galleryRepository, id).galleryImage?.getLink()
    }
    @Transactional
    override fun editGalleryInfo(id: Long, galleryPatchRequest: GalleryPatchRequest): GalleryResponse {
        return RepositoryUtil.getValidatedEntityWithAuthority(galleryRepository, id)
            .apply { this.explanation = galleryPatchRequest.explanation ?: this.explanation}
            .apply { galleryPatchRequest.galleryImageName?.let { name -> this.galleryImage = Image.from(name) }}
            .apply { this.popularThreshold = galleryPatchRequest.popularThreshold ?: this.popularThreshold }
            .let { gallery -> galleryRepository.save(gallery).let { GalleryResponse.from(it)} }
    }
    @Transactional
    override fun deleteGallery(id: Long): GalleryResponse {
        val gallery = RepositoryUtil.getValidatedEntity(galleryRepository, id)
        galleryRepository.delete(gallery)
        return GalleryResponse.from(gallery)
    }

    override fun getGallery(id: Long): GalleryResponse {
        val gallery = RepositoryUtil.getValidatedEntity(galleryRepository, id)
        return GalleryResponse.from(gallery)
    }

    override fun searchGallery(keyword: String?, pageRequestDTO: PageRequestDTO): PageResponseDTO<GalleryResponse> {
        val galleryPages = galleryRepository.searchGallery(keyword, pageRequestDTO.getPageable())
        val content = galleryPages.content.map { GalleryResponse.from(it) }
        return PageResponseDTO.of(pageRequestDTO, content, galleryPages.totalElements.toInt())
    }

    override fun availableGalleryTitle(availableRequest: AvailableRequest): AvailableResponse {
        return availableTitleCheckList.checkValueAvailable(availableRequest.wantCheckValue)
    }
}