package com.ricu.ricukotlin.domain.board.repository

import com.querydsl.core.BooleanBuilder
import com.ricu.ricukotlin.domain.board.dto.BoardSearchRequest
import com.ricu.ricukotlin.domain.board.model.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QueryDslBoardRepository {
    fun searchBoard(pageable: Pageable, galleryId: Long
                    ,boardSearchRequest: BoardSearchRequest): Page<Board>
}