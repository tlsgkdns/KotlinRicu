package com.ricu.ricukotlin.domain.board.repository

import com.ricu.ricukotlin.domain.board.model.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long>, QueryDslBoardRepository {

}