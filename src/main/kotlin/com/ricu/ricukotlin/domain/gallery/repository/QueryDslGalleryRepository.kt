package com.ricu.ricukotlin.domain.gallery.repository

import com.ricu.ricukotlin.domain.gallery.model.Gallery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QueryDslGalleryRepository {
    fun searchGallery(keywordDTO: String?, pageable: Pageable): Page<Gallery>
}