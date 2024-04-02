package com.ricu.ricukotlin.global.common.available.checker

import org.springdoc.api.ErrorMessage

class RegexChecker(
    val regex: Regex = Regex("^[a-zA-Z0-9|s]*$"),
    override val errorMessage: String = "값은 영어 혹은 숫자로 이루어져야합니다."
): ValueChecker {
    override fun check(checkValue: String): Boolean {
        return checkValue.matches(regex = regex)
    }
}