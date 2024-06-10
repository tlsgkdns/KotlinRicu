package com.ricu.ricukotlin.domain.member.dto

import com.ricu.ricukotlin.domain.member.model.MemberType

data class MemberSignInResponse(
    val name: String?,
    val type: MemberType,
    val token: String
)