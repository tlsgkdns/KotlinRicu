package com.ricu.ricukotlin.domain.board.service

import com.ricu.ricukotlin.domain.board.dto.BoardCreateRequest
import com.ricu.ricukotlin.domain.board.dto.BoardEditRequest
import com.ricu.ricukotlin.domain.board.dto.BoardResponse
import com.ricu.ricukotlin.domain.board.dto.BoardSearchRequest
import com.ricu.ricukotlin.domain.board.repository.BoardRepository
import com.ricu.ricukotlin.domain.gallery.repository.GalleryRepository
import com.ricu.ricukotlin.domain.member.repository.MemberRepository
import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.common.PageResponseDTO
import com.ricu.ricukotlin.global.util.RepositoryUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val galleryRepository: GalleryRepository,
    private val memberRepository: MemberRepository
): BoardService {
    @Transactional
    override fun writeBoard(galleryId: Long, boardRequest: BoardCreateRequest): BoardResponse {
        val writeBoard = boardRequest.to(galleryId, galleryRepository).let { boardRepository.save(it) }
        return BoardResponse.from(writeBoard)
    }

    @Transactional
    override fun removeBoard(bno: Long) {
        val board = RepositoryUtil.getValidatedEntityWithAuthority(boardRepository, bno)
        boardRepository.delete(board)
    }

    override fun readBoard(bno: Long): BoardResponse {
        return RepositoryUtil.getValidatedEntity(boardRepository, bno)
            .let { BoardResponse.from(it) }
    }

    @Transactional
    override fun editBoard(bno: Long, boardRequest: BoardEditRequest): BoardResponse {
        val board = RepositoryUtil.getValidatedEntityWithAuthority(boardRepository, bno)
        return board
            .apply { this.title = boardRequest.title }
            .apply { this.content = boardRequest.content }
            .let { boardRepository.save(it) }
            .let { BoardResponse.from(it) }
    }

    @Transactional
    override fun addView(bno: Long): BoardResponse {
        return RepositoryUtil.getValidatedEntity(boardRepository, bno).addView().let {
            boardRepository.save(it)
            BoardResponse.from(it)
        }
    }

    @Transactional
    override fun clickLike(bno: Long): BoardResponse {
        val board = RepositoryUtil.getValidatedEntity(boardRepository, bno)
        if (board.didMemberLikeThisBoard()) board.removeLike()
        else board.addLike()
        boardRepository.save(board)
        return board.let { BoardResponse.from(it) }
    }

    override fun getBoards(galleryId: Long, boardSearchRequest: BoardSearchRequest,
                           pageRequestDTO: PageRequestDTO): PageResponseDTO<BoardResponse> {
        val boardPages = boardRepository.searchBoard(
            galleryId = galleryId,
            boardSearchRequest = boardSearchRequest,
            pageable = pageRequestDTO.getPageable()
        )
        val content = boardPages.content.map { BoardResponse.from(it) }
        return PageResponseDTO.of(pageRequestDTO, content, boardPages.totalElements.toInt())
    }
}