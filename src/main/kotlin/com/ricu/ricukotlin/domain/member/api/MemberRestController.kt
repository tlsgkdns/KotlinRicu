package com.ricu.ricukotlin.domain.member.api

import com.ricu.ricukotlin.domain.member.dto.*
import com.ricu.ricukotlin.domain.member.service.MemberService
import com.ricu.ricukotlin.global.common.available.dto.AvailableRequest
import com.ricu.ricukotlin.global.common.available.dto.AvailableResponse
import com.ricu.ricukotlin.global.util.SecurityUtil
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberRestController(
    private val memberService: MemberService
) {
    @GetMapping("/{memberId}")
    fun getMember(@PathVariable memberId: String): ResponseEntity<MemberResponse>
    {
        return memberService.getMember(memberId)
            .let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }

    @PostMapping("/signUp")
    fun registerMember(@RequestBody memberRegisterRequest: MemberRegisterRequest): ResponseEntity<MemberResponse>
    {
        return memberService.registerMember(memberRegisterRequest)
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }
    @PostMapping("/signIn")
    fun signInMember(@RequestBody memberSignInRequest: MemberSignInRequest): ResponseEntity<MemberSignInResponse>
    {
        return memberService.signIn(memberSignInRequest)
            .let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }

    @PatchMapping("/{memberId}")
    fun modifyMember(@PathVariable memberId: String, @RequestBody memberModifyRequest: MemberModifyRequest)
    : ResponseEntity<MemberResponse>
    {
        return memberService.editMember(memberId, memberModifyRequest)
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @DeleteMapping("/{memberId}")
    fun withdrawMember(@PathVariable memberId: String): ResponseEntity<UInt>
    {
        return memberService.withdrawMember(memberId)
            .let { ResponseEntity.status(HttpStatus.NOT_FOUND).build() }
    }
    @GetMapping("/username")
    fun getLoginMemberUsername(): ResponseEntity<String>
    {
        return ResponseEntity.status(HttpStatus.OK).body(SecurityUtil.getUsername())
    }
    @GetMapping("/available/nickname")
    fun isAvailableNickname(availableRequest: AvailableRequest): ResponseEntity<AvailableResponse>
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.isAvailableNickname(availableRequest))
    }

    @GetMapping("/available/username")
    fun isAvailableUsername(availableRequest: AvailableRequest): ResponseEntity<AvailableResponse>
    {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.isAvailableUsername(availableRequest))
    }
}