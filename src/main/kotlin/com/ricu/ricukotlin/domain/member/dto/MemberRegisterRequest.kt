package com.ricu.ricukotlin.domain.member.dto

import com.ricu.ricukotlin.domain.image.model.Image
import com.ricu.ricukotlin.domain.member.model.Member
import org.springframework.security.crypto.password.PasswordEncoder

data class MemberRegisterRequest(
    val nickname: String,
    val username: String,
    val email: String,
    val password: String,
    val profileImageName: String
)
{
    fun to(encoder: PasswordEncoder) = Member(
        username = username,
        email = email,
        password = encoder.encode(password),
        profileImage = Image.from(profileImageName),
        nickname = nickname
    )
}
