package com.ricu.ricukotlin.global.exception

import com.ricu.ricukotlin.global.exception.exception.CustomException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun customExceptionHandler(e: CustomException)
        = e.getErrorResponse()
}