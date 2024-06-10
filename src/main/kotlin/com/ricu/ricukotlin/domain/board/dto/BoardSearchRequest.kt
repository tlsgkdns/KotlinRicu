package com.ricu.ricukotlin.domain.board.dto

data class BoardSearchRequest(
    val keyword: String?,
    val types: String?,
    val popular: Boolean
)
