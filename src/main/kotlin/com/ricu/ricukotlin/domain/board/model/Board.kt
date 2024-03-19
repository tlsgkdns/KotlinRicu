package com.ricu.ricukotlin.domain.board.model

import com.ricu.ricukotlin.global.common.CreatorAuditEntity
import com.ricu.ricukotlin.domain.gallery.model.Gallery
import com.ricu.ricukotlin.domain.member.model.Member
import com.ricu.ricukotlin.domain.member.repository.MemberRepository
import com.ricu.ricukotlin.global.util.RepositoryUtil
import com.ricu.ricukotlin.global.util.SecurityUtil
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
class Board (
    @Column(length = 500, nullable = false)
    var title: String,
    @Column(length = 2000, nullable = false)
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val gallery: Gallery,
): CreatorAuditEntity()
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(fetch = FetchType.LAZY)
    val likeMembers: MutableSet<Member> = mutableSetOf()
    var view: Long = 0L
    fun addLike(memberRepository: MemberRepository): Board
    {
        likeMembers.add(SecurityUtil.getLoginMember(memberRepository))
        return this
    }
    fun removeLike(memberRepository: MemberRepository): Board
    {
        likeMembers.remove(SecurityUtil.getLoginMember(memberRepository))
        return this
    }
    fun addView(): Board
    {
        ++view
        return this
    }
    fun didMemberLikeThisBoard(memberRepository: MemberRepository): Boolean
    {
        return likeMembers.contains(SecurityUtil.getLoginMember(memberRepository))
    }
}