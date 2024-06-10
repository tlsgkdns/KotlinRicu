package com.ricu.ricukotlin.domain.member.repository

import com.ricu.ricukotlin.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, String> {

    fun existsByNickname(nickname: String): Boolean
    fun existsByUsername(username: String): Boolean
}