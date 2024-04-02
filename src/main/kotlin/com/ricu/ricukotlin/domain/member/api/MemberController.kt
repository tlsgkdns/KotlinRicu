package com.ricu.ricukotlin.domain.member.api

import com.ricu.ricukotlin.domain.member.dto.MemberSignInRequest
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/member")
class MemberController {
    @GetMapping("/login")
    fun getLoginPage()
    {

    }
    @GetMapping("/register")
    fun getMemberRegisterPage()
    {

    }
    @GetMapping("/info")
    fun getMemberInfoPage()
    {

    }
    @GetMapping("/edit")
    fun getMemberEditPage()
    {

    }
}