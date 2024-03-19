package com.ricu.ricukotlin.domain.member.service

import com.ricu.ricukotlin.domain.image.model.Image
import com.ricu.ricukotlin.domain.member.dto.*
import com.ricu.ricukotlin.domain.member.repository.MemberRepository
import com.ricu.ricukotlin.global.exception.exception.AlreadyExistMemberException
import com.ricu.ricukotlin.global.infra.security.TokenProvider
import com.ricu.ricukotlin.global.util.RepositoryUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val encoder: PasswordEncoder,
    private val tokenProvider: TokenProvider
): MemberService {
    @Transactional
    override fun registerMember(memberRegisterRequest: MemberRegisterRequest): MemberResponse {
        memberRepository.findByIdOrNull(memberRegisterRequest.username)
            ?.let { throw AlreadyExistMemberException(memberRegisterRequest.username) }
        val member = memberRegisterRequest.to(encoder)
            .let { memberRepository.save(it) }
        return member.let { MemberResponse.from(it) }
    }

    override fun signIn(memberSignInRequest: MemberSignInRequest): MemberSignInResponse {
        val member = RepositoryUtil.getValidatedEntity(memberRepository, memberSignInRequest.username)
            .takeIf { encoder.matches(memberSignInRequest.password, it.password) }
            ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")
        val token = tokenProvider.createToken("${member.username}:${member.roleSet}")
        return MemberSignInResponse(member.username, member.roleSet.first(), token)
    }

    override fun getMember(memberId: String): MemberResponse {
        return RepositoryUtil.getValidatedEntity(memberRepository, memberId)
            .let { MemberResponse.from(it) }
    }

    @Transactional
    override fun editMember(memberId: String, memberModifyRequest: MemberModifyRequest): MemberResponse {
        return RepositoryUtil.getValidatedEntity(memberRepository, memberId)
            .apply { this.email = memberModifyRequest.email ?: this.email }
            .apply { this.nickname = memberModifyRequest.nickname ?: this.nickname}
            .apply { this.profileImage = memberModifyRequest.profileImage?.let { Image.from(it) } ?: this.profileImage }
            .let { MemberResponse.from(it) }
    }

    @Transactional
    override fun withdrawMember(memberId: String) {
        val member = RepositoryUtil.getValidatedEntity(memberRepository, memberId)
        memberRepository.delete(member)
    }
}