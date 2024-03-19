package com.ricu.ricukotlin.domain.member.dto

data class MemberModifyRequest(
    val email: String?,
    val nickname: String?,
    val profileImage: String?
)
