package com.ricu.ricukotlin.global.exception.exception

import com.ricu.ricukotlin.global.exception.ErrorCode

data class NotHaveAuthorityException(
    val username: String,
    val model: String
): CustomException(
    errorCode = ErrorCode.NOT_HAVE_AUTHORITY,
    message = "$username 은/는 $model 에 대한 권한이 없습니다."
)
{
    override fun log() {
        super.logger.error("접근 권한이 없습니다.")
        message?.let { super.logger.info(it) }
    }
}
