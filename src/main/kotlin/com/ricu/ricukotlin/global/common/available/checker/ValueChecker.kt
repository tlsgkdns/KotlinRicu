package com.ricu.ricukotlin.global.common.available.checker

interface ValueChecker
{
    val errorMessage: String
    fun check(checkValue: String): Boolean
}