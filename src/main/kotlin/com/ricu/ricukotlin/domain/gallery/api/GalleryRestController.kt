package com.ricu.ricukotlin.domain.gallery.api

import com.ricu.ricukotlin.domain.gallery.dto.GalleryCreateRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryPatchRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryResponse
import com.ricu.ricukotlin.domain.gallery.service.GalleryService
import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.common.PageResponseDTO
import com.ricu.ricukotlin.global.common.available.dto.AvailableRequest
import com.ricu.ricukotlin.global.common.available.dto.AvailableResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/galleries")
class GalleryRestController(
    private val galleryService: GalleryService
) {
    @GetMapping()
    fun searchGallery(pageRequestDTO: PageRequestDTO, keyword: String?)
        : ResponseEntity<PageResponseDTO<GalleryResponse>>
    {
        return galleryService.searchGallery(keyword, pageRequestDTO).let {
            ResponseEntity.status(HttpStatus.OK).body(it)
        }
    }
    @GetMapping("/{id}")
    fun getGallery(@PathVariable id: Long): ResponseEntity<GalleryResponse>
    {
        return galleryService.getGallery(id).let {
            ResponseEntity.status(HttpStatus.OK).body(it)
        }
    }
    @PostMapping()
    fun createGallery(@RequestBody createRequest: GalleryCreateRequest): ResponseEntity<GalleryResponse>
    {
        return galleryService.createGallery(createRequest).let {
            ResponseEntity.status(HttpStatus.CREATED).body(it)
        }
    }
    @PatchMapping("/{id}")
    fun modifyGallery(@PathVariable id: Long, @RequestBody patchRequest: GalleryPatchRequest): ResponseEntity<GalleryResponse>
    {
        return galleryService.editGalleryInfo(id, patchRequest).let {
            ResponseEntity.status(HttpStatus.OK).body(it)
        }
    }
    @DeleteMapping("/{id}")
    fun deleteGallery(@PathVariable id: Long): ResponseEntity<GalleryResponse>
    {
        return galleryService.deleteGallery(id).let {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(it)
        }
    }
    @GetMapping("/available/title")
    fun isAvailableTitle(availableRequest: AvailableRequest): ResponseEntity<AvailableResponse>
    {
        return galleryService.availableGalleryTitle(availableRequest).let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }
}