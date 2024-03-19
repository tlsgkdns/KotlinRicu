package com.ricu.ricukotlin.global.infra.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: AuthenticationEntryPoint
) {
    private val allowedUrls = arrayOf("/", "/swagger-ui/**", "/v3/**", "/members/signUp", "/members/signIn")
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .authorizeHttpRequests{ requests ->
            requests.requestMatchers(*allowedUrls).permitAll()
            requests.anyRequest().authenticated()
        }
        .sessionManagement{it.sessionCreationPolicy(STATELESS)}
        .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter::class.java)
        .exceptionHandling{
            it.authenticationEntryPoint(authenticationEntryPoint)
        }
        .build()!!
}