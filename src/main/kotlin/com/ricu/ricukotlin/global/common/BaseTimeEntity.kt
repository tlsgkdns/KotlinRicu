package com.ricu.ricukotlin.global.common

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @CreationTimestamp
    @Column(nullable = false)
    open var createdAt: ZonedDateTime = ZonedDateTime.now()
}