package com.ricu.ricukotlin.global.common

import com.ricu.ricukotlin.domain.member.model.Member
import com.ricu.ricukotlin.domain.member.repository.MemberRepository
import com.ricu.ricukotlin.global.util.RepositoryUtil
import org.springframework.data.domain.AuditorAware
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomAuditorAware(
    private val memberRepository: MemberRepository
): AuditorAware<Member> {
    override fun getCurrentAuditor(): Optional<Member> {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map { it.authentication }
            .map { it.principal as UserDetails }
            .map {
                memberRepository.findByIdOrNull(it.username)
            }
    }
}