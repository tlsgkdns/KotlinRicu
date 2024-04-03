package com.ricu.ricukotlin.domain.comment.service
import com.ricu.ricukotlin.domain.board.repository.BoardRepository
import com.ricu.ricukotlin.domain.comment.dto.CommentRequest
import com.ricu.ricukotlin.domain.comment.dto.CommentResponse
import com.ricu.ricukotlin.domain.comment.repository.CommentRepository
import com.ricu.ricukotlin.global.common.PageRequestDTO
import com.ricu.ricukotlin.global.common.PageResponseDTO
import com.ricu.ricukotlin.global.util.RepositoryUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val boardRepository: BoardRepository,
    private val commentRepository: CommentRepository
): CommentService {
    @Transactional
    override fun writeComment(boardId: Long, commentRequest: CommentRequest): CommentResponse {
        val newComment = commentRequest.to(boardRepository, boardId).run { commentRepository.save(this) }
        return CommentResponse.from(newComment)
    }
    override fun getComment(commentId: Long): CommentResponse {
        return CommentResponse.from(RepositoryUtil.getValidatedEntity(commentRepository, commentId))
    }
    @Transactional
    override fun removeComment(commentId: Long) {
        val comment = RepositoryUtil.getValidatedEntityWithAuthority(commentRepository, commentId)
        commentRepository.delete(comment)
    }
    @Transactional
    override fun editCommentText(commentId: Long, commentRequest: CommentRequest): CommentResponse {
        return RepositoryUtil.getValidatedEntityWithAuthority(commentRepository, commentId)
            .apply { this.commentText = commentRequest.commentText }
            .let { commentRepository.save(it) }
            .let { CommentResponse.from(it) }
    }

    override fun getComments(boardId: Long, pageRequestDTO: PageRequestDTO): PageResponseDTO<CommentResponse> {
        val commentPage = commentRepository.getCommentsByBoardId(boardId, pageRequestDTO.getPageable())
        return PageResponseDTO.of(pageRequestDTO, commentPage.content.map { CommentResponse.from(it) }, commentPage.totalElements.toInt())
    }
    override fun getCommentCount(boardId: Long): Int {
        return commentRepository.countByBoardId(boardId)
    }
}