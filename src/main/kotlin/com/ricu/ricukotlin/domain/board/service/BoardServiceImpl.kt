package com.ricu.ricukotlin.domain.board.service

import com.ricu.ricukotlin.domain.board.dto.BoardCreateRequest
import com.ricu.ricukotlin.domain.board.dto.BoardModifyRequest
import com.ricu.ricukotlin.domain.board.dto.BoardResponse
import com.ricu.ricukotlin.domain.board.repository.BoardRepository
import com.ricu.ricukotlin.domain.gallery.repository.GalleryRepository
import com.ricu.ricukotlin.domain.member.repository.MemberRepository
import com.ricu.ricukotlin.global.util.RepositoryUtil
import com.ricu.ricukotlin.global.util.SecurityUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val galleryRepository: GalleryRepository,
    private val memberRepository: MemberRepository
): BoardService {
    @Transactional
    override fun writeBoard(galleryId: String, boardRequest: BoardCreateRequest): BoardResponse {
        return boardRequest.to(galleryId, galleryRepository).let { boardRepository.save(it) }
            .let { BoardResponse.from(it) }
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
    override fun modifyBoard(bno: Long, boardRequest: BoardModifyRequest): BoardResponse {
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
    override fun addLike(bno: Long): BoardResponse {
        return RepositoryUtil.getValidatedEntity(boardRepository, bno).addLike(memberRepository).let {
            boardRepository.save(it)
            BoardResponse.from(it)
        }
    }

    @Transactional
    override fun removeLike(bno: Long): BoardResponse {
        return RepositoryUtil.getValidatedEntity(boardRepository, bno).removeLike(memberRepository).let {
            boardRepository.save(it)
            BoardResponse.from(it)
        }
    }

}