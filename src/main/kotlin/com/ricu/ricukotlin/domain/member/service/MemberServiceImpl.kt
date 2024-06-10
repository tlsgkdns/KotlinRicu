package com.ricu.ricukotlin.domain.member.service

import com.ricu.ricukotlin.domain.image.model.Image
import com.ricu.ricukotlin.domain.member.dto.*
import com.ricu.ricukotlin.domain.member.repository.MemberRepository
import com.ricu.ricukotlin.global.exception.exception.AlreadyExistMemberException
import com.ricu.ricukotlin.global.common.available.AvailableCheckList
import com.ricu.ricukotlin.global.common.available.checker.DuplicateChecker
import com.ricu.ricukotlin.global.common.available.checker.LengthChecker
import com.ricu.ricukotlin.global.common.available.checker.RegexChecker
import com.ricu.ricukotlin.global.common.available.dto.AvailableRequest
import com.ricu.ricukotlin.global.common.available.dto.AvailableResponse
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
    private val nicknameAvailableCheckList = AvailableCheckList(
        listOf(
            DuplicateChecker(memberRepository::existsByNickname),
            LengthChecker(3, 20),
            RegexChecker()
            )
    )
    private val usernameAvailableCheckList = AvailableCheckList(
        listOf(
            DuplicateChecker(memberRepository::existsByUsername),
            LengthChecker(3, 20),
            RegexChecker()
        )
    )

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
    override fun editMember(memberId: String, memberEditRequest: MemberEditRequest): MemberResponse {
        return RepositoryUtil.getValidatedEntity(memberRepository, memberId)
            .apply { this.email = memberEditRequest.email ?: this.email }
            .apply { this.nickname = memberEditRequest.nickname ?: this.nickname}
            .apply { this.profileImage = memberEditRequest.profileImage?.let { Image.from(it) } ?: this.profileImage }
            .let { MemberResponse.from(it) }
    }

    @Transactional
    override fun withdrawMember(memberId: String) {
        val member = RepositoryUtil.getValidatedEntity(memberRepository, memberId)
        memberRepository.delete(member)
    }

    override fun isAvailableNickname(availableRequest: AvailableRequest): AvailableResponse {
        return nicknameAvailableCheckList.checkValueAvailable(availableRequest.wantCheckValue)
    }

    override fun isAvailableUsername(availableRequest: AvailableRequest): AvailableResponse {
       return usernameAvailableCheckList.checkValueAvailable(availableRequest.wantCheckValue)
    }

}