package com.ricu.ricukotlin.domain.comment.model

import com.ricu.ricukotlin.domain.board.model.Board
import com.ricu.ricukotlin.global.common.CreatorAuditEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "Comment")
class Comment(
    var commentText: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val board: Board,
): CreatorAuditEntity()
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}