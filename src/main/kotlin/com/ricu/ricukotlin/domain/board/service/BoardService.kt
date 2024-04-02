package com.ricu.ricukotlin.domain.board.service

import com.ricu.ricukotlin.domain.board.dto.BoardCreateRequest
import com.ricu.ricukotlin.domain.board.dto.BoardModifyRequest
import com.ricu.ricukotlin.domain.board.dto.BoardResponse
import com.ricu.ricukotlin.domain.board.dto.BoardSearchRequest

import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.common.PageResponseDTO
import org.springframework.data.domain.Page

interface BoardService {
    fun writeBoard(galleryId: Long, boardRequest: BoardCreateRequest): BoardResponse
    fun removeBoard(bno: Long)
    fun readBoard(bno: Long): BoardResponse
    fun modifyBoard(bno: Long, boardRequest: BoardModifyRequest): BoardResponse
    fun addView(bno: Long): BoardResponse
    fun clickLike(bno: Long): BoardResponse
    fun getBoards(galleryId: Long, boardSearchRequest: BoardSearchRequest, pageRequestDTO: PageRequestDTO): PageResponseDTO<BoardResponse>
}