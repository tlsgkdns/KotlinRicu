package com.ricu.ricukotlin.global.common

import com.ricu.ricukotlin.domain.member.model.Member
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class CreatorAuditEntity: BaseTimeEntity()
{
    @ManyToOne(fetch = FetchType.LAZY)
    @CreatedBy
    @JoinColumn(name = "creator")
    open var creator: Member? = null
}