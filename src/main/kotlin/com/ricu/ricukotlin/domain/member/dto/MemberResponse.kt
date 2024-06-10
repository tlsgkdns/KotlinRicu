package com.ricu.ricukotlin.domain.member.dto

import com.ricu.ricukotlin.domain.member.model.Member
import java.time.LocalDateTime

data class MemberResponse(
    val username: String,
    val email: String,
    val profileImageName: String?,
    val nickname: String,
    val registeredDate: LocalDateTime,
    val memberRole: String
)
{
    companion object
    {
        fun from(member: Member): MemberResponse
        {
            return MemberResponse(
                username = member.username,
                email = member.email,
                profileImageName = member.profileImage?.getLink(),
                nickname = member.nickname,
                registeredDate = member.createdAt.toLocalDateTime(),
                memberRole = member.roleSet.map {it.toString()}.toString()
            )
        }
    }
}
