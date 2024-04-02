package com.ricu.ricukotlin.global.common

import kotlin.math.ceil
import kotlin.math.min

data class PageResponseDTO<T>(
    val page: Int,
    val size: Int,
    val total: Int,
    val start: Int,
    val end: Int,
    val prev: Boolean,
    val next: Boolean,
    val dtoList: List<T>
)
{
    companion object
    {
        fun<T> of(pageRequestDTO: PageRequestDTO, dtoList: List<T>, total: Int): PageResponseDTO<T>
        {
            val page = pageRequestDTO.page
            val size = pageRequestDTO.size
            val last = ceil(total / size.toDouble())
            val end = min(ceil(page / 10.0) * 10, last).toInt()
            val start = (ceil(page / 10.0) * 10).toInt() - 9
            return PageResponseDTO(
                page = pageRequestDTO.page,
                size = size,
                total = total,
                dtoList = dtoList,
                start = start,
                end = end,
                next = total > end * size,
                prev = (start > 1)
            )
        }
    }
}