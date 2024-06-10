package com.ricu.ricukotlin.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: Long, val httpStatus: HttpStatus, val message: String)
{
    MODEL_NOT_FOUND(1001, HttpStatus.NOT_FOUND, "해당 Model을 찾을 수 없습니다."),
    INVALID_IMAGE_NAME(1002, HttpStatus.BAD_REQUEST, "유효하지 않은 Image입니다."),
    ALREADY_EXIST_MEMBER(1003, HttpStatus.BAD_REQUEST, "이미 존재하는 Member입니다."),
    NOT_HAVE_AUTHORITY(1004, HttpStatus.BAD_REQUEST, "해당 Model에 대한 접근 권한이 없습니다.")
}