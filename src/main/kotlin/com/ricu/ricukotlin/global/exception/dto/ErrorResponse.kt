package com.ricu.ricukotlin.global.exception.dto

data class ErrorResponse(
    val message: String,
    val errorCode: Long
)