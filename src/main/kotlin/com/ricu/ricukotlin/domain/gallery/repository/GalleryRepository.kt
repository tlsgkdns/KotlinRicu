package com.ricu.ricukotlin.domain.gallery.repository

import com.ricu.ricukotlin.domain.gallery.model.Gallery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GalleryRepository: JpaRepository<Gallery, String>, QueryDslGalleryRepository