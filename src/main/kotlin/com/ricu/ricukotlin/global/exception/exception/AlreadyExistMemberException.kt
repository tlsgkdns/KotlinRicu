package com.ricu.ricukotlin.global.exception.exception

import com.ricu.ricukotlin.global.exception.ErrorCode

class AlreadyExistMemberException(
    memberUsername: String
): CustomException(
    message = "${memberUsername}은 이미 존재하는 멤버입니다.",
    errorCode = ErrorCode.ALREADY_EXIST_MEMBER
) {
    override fun log() {
        super.logger.error("이미 존재하는 멤버입니다.")
        message?.let { super.logger.info(it) }
    }
}