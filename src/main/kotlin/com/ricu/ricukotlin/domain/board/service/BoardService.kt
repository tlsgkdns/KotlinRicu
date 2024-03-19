package com.ricu.ricukotlin.domain.board.service

import com.ricu.ricukotlin.domain.board.dto.BoardCreateRequest
import com.ricu.ricukotlin.domain.board.dto.BoardModifyRequest
import com.ricu.ricukotlin.domain.board.dto.BoardResponse

interface BoardService {
    fun writeBoard(galleryId: String, boardRequest: BoardCreateRequest): BoardResponse
    fun removeBoard(bno: Long)
    fun readBoard(bno: Long): BoardResponse
    fun modifyBoard(bno: Long, boardRequest: BoardModifyRequest): BoardResponse
    fun addView(bno: Long): BoardResponse
    fun addLike(bno: Long): BoardResponse
    fun removeLike(bno: Long): BoardResponse
}