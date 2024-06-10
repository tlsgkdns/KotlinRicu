package com.ricu.ricukotlin.domain.member.model

import com.ricu.ricukotlin.domain.image.model.Image
import com.ricu.ricukotlin.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Member(
    @Id
    @Column(name = "username")
    var username: String,
    @Column(name = "password")
    var password: String,
    @Column(length = 50, nullable = false, unique = true)
    var nickname: String,
    @Column(length = 100, nullable = false)
    var email: String,
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var profileImage : Image? = null
): BaseTimeEntity()
{
    @ElementCollection(fetch = FetchType.LAZY)
    val roleSet: Set<MemberType> = setOf(MemberType.USER)
}