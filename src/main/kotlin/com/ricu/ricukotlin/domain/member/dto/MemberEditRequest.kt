package com.ricu.ricukotlin.domain.member.dto

data class MemberEditRequest(
    val email: String?,
    val nickname: String?,
    val profileImage: String?
)
