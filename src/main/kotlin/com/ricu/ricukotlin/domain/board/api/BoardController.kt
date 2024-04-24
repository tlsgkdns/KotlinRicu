package com.ricu.ricukotlin.domain.board.api

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/gallery/board")
class BoardController {
    @GetMapping("/list")
    fun boardList()
    {

    }
    @GetMapping("/write")
    fun writeBoard()
    {

    }
    @GetMapping("/read")
    fun readBoard()
    {

    }
    @GetMapping("edit")
    fun editBoard()
    {

    }
}