package com.ricu.ricukotlin.domain.gallery.api

import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/gallery")
class GalleryController {

    @GetMapping(path = ["/", "/home"])
    fun goHome()
    {

    }
    @GetMapping("/create")
    fun createCreate()
    {

    }
    @GetMapping("/edit")
    fun editGallery()
    {

    }
}