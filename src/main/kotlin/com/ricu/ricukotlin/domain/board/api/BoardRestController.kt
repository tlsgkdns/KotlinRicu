package com.ricu.ricukotlin.domain.board.api

import com.ricu.ricukotlin.domain.board.dto.BoardCreateRequest
import com.ricu.ricukotlin.domain.board.dto.BoardEditRequest
import com.ricu.ricukotlin.domain.board.dto.BoardResponse
import com.ricu.ricukotlin.domain.board.dto.BoardSearchRequest
import com.ricu.ricukotlin.domain.board.service.BoardService
import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.common.PageResponseDTO
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
@RequestMapping("/galleries/{galleryId}/boards")
class BoardRestController(
    private val boardService: BoardService
) {
    @GetMapping("/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ResponseEntity<BoardResponse>
    {
        return boardService.readBoard(boardId).let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }
    @PostMapping()
    fun writeBoard(@PathVariable galleryId: Long, @RequestBody boardCreateRequest: BoardCreateRequest): ResponseEntity<BoardResponse>
    {
        return boardService.writeBoard(galleryId, boardCreateRequest).let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @PatchMapping("/{boardId}")
    fun editBoard(@PathVariable boardId: Long, @RequestBody boardEditRequest: BoardEditRequest)
    : ResponseEntity<BoardResponse>
    {
        return boardService.editBoard(boardId, boardEditRequest).let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }
    @DeleteMapping("/{boardId}")
    fun deleteBoard(@PathVariable boardId: Long): ResponseEntity<Long>
    {
        return boardService.removeBoard(boardId).let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }
    }

    @GetMapping("/")
    fun searchBoard(@PathVariable galleryId: Long, pageRequestDTO: PageRequestDTO
                    , boardSearchRequest: BoardSearchRequest): ResponseEntity<PageResponseDTO<BoardResponse>>
    {
        return boardService.getBoards(galleryId, boardSearchRequest, pageRequestDTO).let {
            ResponseEntity.status(HttpStatus.OK).body(it)
        }
    }
    @PatchMapping("/{boardId}/likes")
    fun clickLike(@PathVariable boardId: Long): ResponseEntity<BoardResponse>
    {
        return boardService.clickLike(boardId).let { ResponseEntity.status(HttpStatus.OK).build() }
    }
    @PatchMapping("/{boardId}/views")
    fun addView(@PathVariable boardId: Long): ResponseEntity<BoardResponse>
    {
        return boardService.addView(boardId).let { ResponseEntity.status(HttpStatus.OK).build() }
    }
}