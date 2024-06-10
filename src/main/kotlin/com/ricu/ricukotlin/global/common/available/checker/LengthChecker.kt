package com.ricu.ricukotlin.global.common.available.checker

class LengthChecker(
    private val minLength: Int,
    private val maxLength: Int,
    override val errorMessage: String = "길이는 $minLength 이상 $maxLength 이하여야 합니다."
): ValueChecker {
    override fun check(checkValue: String): Boolean {
        return checkValue.length >= this.minLength && checkValue.length <= this.maxLength
    }
}