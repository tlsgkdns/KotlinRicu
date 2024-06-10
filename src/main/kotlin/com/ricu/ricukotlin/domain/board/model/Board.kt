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
    @JoinColumn(referencedColumnName = "galleryId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val gallery: Gallery,
): CreatorAuditEntity()
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ElementCollection
    val likeMembers: MutableList<String> = mutableListOf()

    var view: Long = 0L
    fun addLike(): Board
    {
        likeMembers.add(SecurityUtil.getUsername())
        return this
    }
    fun removeLike(): Board
    {
        likeMembers.remove(SecurityUtil.getUsername())
        return this
    }
    fun addView(): Board
    {
        ++view
        return this
    }
    fun didMemberLikeThisBoard(): Boolean
    {
        return likeMembers.contains(SecurityUtil.getUsername())
    }
}