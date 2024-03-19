package com.ricu.ricukotlin.domain.member.dto

data class MemberSignInRequest(
    val username: String,
    val password: String
)