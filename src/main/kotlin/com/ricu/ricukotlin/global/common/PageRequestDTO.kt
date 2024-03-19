package com.ricu.ricukotlin.global.common

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class PageRequestDTO(
    val page: Int = 1,
    val size: Int = 10,
    val link: String? = null
)
{
    fun getPageable(): Pageable
    {
        return PageRequest.of(page - 1, size)
    }
}
