package com.ricu.ricukotlin.domain.gallery.model

import com.ricu.ricukotlin.global.common.CreatorAuditEntity
import com.ricu.ricukotlin.domain.image.model.Image
import jakarta.persistence.*


@Entity
class Gallery(
    @Column(length = 500)
    var explanation: String,
    @Column(length = 30)
    var title: String,
    var popularThreshold: Int = 5,
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var galleryImage: Image? = null,
): CreatorAuditEntity()
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val galleryId: Long? = 0L
}