package com.ricu.ricukotlin.domain.board.api

import com.ricu.ricukotlin.domain.board.dto.BoardCreateRequest
import com.ricu.ricukotlin.domain.board.dto.BoardModifyRequest
import com.ricu.ricukotlin.domain.board.dto.BoardResponse
import com.ricu.ricukotlin.domain.board.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/galleries/{galleryId}")
class BoardRestController(
    private val boardService: BoardService
) {
    @GetMapping("/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ResponseEntity<BoardResponse>
    {
        return boardService.readBoard(boardId).let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }
    @PostMapping()
    fun writeBoard(@PathVariable galleryId: String, @RequestBody boardCreateRequest: BoardCreateRequest): ResponseEntity<BoardResponse>
    {
        return boardService.writeBoard(galleryId, boardCreateRequest).let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @PatchMapping("/{boardId}")
    fun modifyBoard(@PathVariable boardId: Long, @RequestBody boardModifyRequest: BoardModifyRequest)
    : ResponseEntity<BoardResponse>
    {
        return boardService.modifyBoard(boardId, boardModifyRequest).let { ResponseEntity.status(HttpStatus.OK).body(it) }
    }
    @DeleteMapping("/{boardId}")
    fun deleteBoard(@PathVariable boardId: Long): ResponseEntity<UInt>
    {
        return boardService.removeBoard(boardId).let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }
    }

    @PostMapping("/{boardId}/like")
    fun addLike(@PathVariable boardId: Long): ResponseEntity<BoardResponse>
    {
        return boardService.addLike(boardId).let { ResponseEntity.status(HttpStatus.OK).build() }
    }
    @PostMapping("/{boardId}/view")
    fun addView(@PathVariable boardId: Long): ResponseEntity<BoardResponse>
    {
        return boardService.addView(boardId).let { ResponseEntity.status(HttpStatus.OK).build() }
    }
    @DeleteMapping("/{boardId}/like")
    fun removeLike(@PathVariable boardId: Long): ResponseEntity<BoardResponse>
    {
        return boardService.removeLike(boardId).let { ResponseEntity.status(HttpStatus.OK).build() }
    }
}