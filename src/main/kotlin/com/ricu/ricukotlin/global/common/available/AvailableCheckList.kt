package com.ricu.ricukotlin.global.common.available

import com.ricu.ricukotlin.global.common.available.checker.ValueChecker
import com.ricu.ricukotlin.global.common.available.dto.AvailableResponse

class AvailableCheckList(
    val checkList: List<ValueChecker> = listOf()
) {
    fun checkValueAvailable(checkValue: String): AvailableResponse
    {
        for(checker in checkList)
            if(!checker.check(checkValue))
            {
                return AvailableResponse(false, checker.errorMessage)
            }
        return AvailableResponse(true, "사용할 수 있습니다.")
    }
}