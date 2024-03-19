package com.ricu.ricukotlin.domain.gallery.api

import com.ricu.ricukotlin.domain.gallery.dto.GalleryCreateRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryPatchRequest
import com.ricu.ricukotlin.domain.gallery.dto.GalleryResponse
import com.ricu.ricukotlin.domain.gallery.dto.GallerySearchKeywordDTO
import com.ricu.ricukotlin.domain.gallery.service.GalleryService
import com.ricu.ricukotlin.global.common.PageRequestDTO
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
    fun searchGallery(keywordDTO: GallerySearchKeywordDTO, pageRequestDTO: PageRequestDTO)
        : ResponseEntity<Page<GalleryResponse>>
    {
        return galleryService.searchGallery(keywordDTO, pageRequestDTO).let {
            ResponseEntity.status(HttpStatus.OK).body(it)
        }
    }
    @GetMapping("/{galleryUrl}")
    fun getGallery(@PathVariable galleryUrl: String): ResponseEntity<GalleryResponse>
    {
        return galleryService.getGallery(galleryUrl).let {
            ResponseEntity.status(HttpStatus.OK).body(it)
        }
    }
    @PostMapping()
    fun createGallery(@RequestBody createRequest: GalleryCreateRequest)
    {
        return galleryService.createGallery(createRequest).let {
            ResponseEntity.status(HttpStatus.CREATED).body(it)
        }
    }
    @PatchMapping("/{galleryUrl}")
    fun modifyGallery(@PathVariable galleryUrl: String, @RequestBody patchRequest: GalleryPatchRequest)
    {
        return galleryService.editGalleryInfo(galleryUrl, patchRequest).let {
            ResponseEntity.status(HttpStatus.OK).body(it)
        }
    }
    @DeleteMapping("/{galleryUrl}")
    fun deleteGallery(@PathVariable galleryUrl: String)
    {
        return galleryService.deleteGallery(galleryUrl).let {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(it)
        }
    }
}