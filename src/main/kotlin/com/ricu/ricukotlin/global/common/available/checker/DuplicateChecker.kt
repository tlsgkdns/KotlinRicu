package com.ricu.ricukotlin.global.common.available.checker

import org.springdoc.api.ErrorMessage

class DuplicateChecker(
    val duplicateCheckFunction: (String) -> Boolean,
    override val errorMessage: String = "이미 존재하는 값입니다."
): ValueChecker
{
    override fun check(checkValue: String): Boolean {
        return !duplicateCheckFunction(checkValue)
    }
}