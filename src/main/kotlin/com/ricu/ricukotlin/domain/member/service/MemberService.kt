package com.ricu.ricukotlin.domain.member.service

import com.ricu.ricukotlin.domain.member.dto.*
import com.ricu.ricukotlin.global.common.available.dto.AvailableRequest
import com.ricu.ricukotlin.global.common.available.dto.AvailableResponse

interface MemberService {
    fun registerMember(memberRegisterRequest: MemberRegisterRequest): MemberResponse
    fun signIn(memberSignInRequest: MemberSignInRequest): MemberSignInResponse
    fun getMember(memberId: String): MemberResponse
    fun editMember(memberId: String, memberModifyRequest: MemberModifyRequest): MemberResponse
    fun withdrawMember(memberId: String)
    fun isAvailableNickname(availableRequest: AvailableRequest) : AvailableResponse
    fun isAvailableUsername(availableRequest: AvailableRequest) : AvailableResponse
}