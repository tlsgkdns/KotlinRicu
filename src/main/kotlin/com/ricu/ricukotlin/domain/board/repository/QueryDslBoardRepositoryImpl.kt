package com.ricu.ricukotlin.domain.board.repository

import com.querydsl.core.BooleanBuilder
import com.ricu.ricukotlin.domain.board.dto.BoardSearchRequest
import com.ricu.ricukotlin.domain.board.model.Board
import com.ricu.ricukotlin.domain.board.model.QBoard
import com.ricu.ricukotlin.domain.gallery.model.QGallery
import com.ricu.ricukotlin.domain.member.model.QMember
import com.ricu.ricukotlin.global.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class QueryDslBoardRepositoryImpl: QueryDslBoardRepository, QueryDslSupport() {
    override fun searchBoard(pageable: Pageable, galleryId: Long,
                             boardSearchRequest: BoardSearchRequest): Page<Board> {
        val board = QBoard.board
        val member = QMember.member
        val gallery = QGallery.gallery
        val booleanBuilder = BooleanBuilder()
        val (keyword, types, popular) = boardSearchRequest
        keyword?.run {
            types?.forEach {
                when(it)
                {
                    't' -> booleanBuilder.or(board.title.contains(this))
                    'w' -> booleanBuilder.or(board.creator.nickname.contains(this))
                    'c' -> booleanBuilder.or(board.content.contains(this))
                }
            }
        }
        booleanBuilder.and(board.gallery.galleryId.eq(galleryId))
        val content = queryFactory.select(board)
            .from(board)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .innerJoin(gallery).on(gallery.galleryId.eq(board.gallery.galleryId))
            .innerJoin(member).on(member.username.eq(board.creator.username))
            .where(booleanBuilder)
            .run {
                if(popular) this.where(board.likeMembers.size().goe(board.gallery.popularThreshold))
                else this
            }
            .fetch()
        val total = queryFactory.select(board.count())
            .from(board)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .innerJoin(board.gallery, gallery)
            .innerJoin(board.creator, member)
            .where(booleanBuilder)
            .run {
                if(popular) this.where(board.likeMembers.size().goe(board.gallery.popularThreshold))
                else this
            }
        return PageImpl(content, pageable, total.fetchOne() ?: 0)
    }
}