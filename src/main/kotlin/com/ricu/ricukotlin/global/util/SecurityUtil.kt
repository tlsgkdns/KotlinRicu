package com.ricu.ricukotlin.global.util

import com.ricu.ricukotlin.domain.member.model.Member
import com.ricu.ricukotlin.domain.member.repository.MemberRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class SecurityUtil
{
    companion object
    {
        fun getUsername(): String
        {
            return (SecurityContextHolder.getContext()?.authentication?.principal as? UserDetails)?.username ?: "anonymous"
        }

        fun getLoginMember(memberRepository: MemberRepository): Member
        {
            return RepositoryUtil.getValidatedEntity(memberRepository, getUsername())
        }
    }
}