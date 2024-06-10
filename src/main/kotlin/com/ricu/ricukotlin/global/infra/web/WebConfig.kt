package com.ricu.ricukotlin.global.infra.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig: WebMvcConfigurer {

    @Override
    override fun addResourceHandlers(registry: ResourceHandlerRegistry)
    {
        registry.addResourceHandler("/js/**")
            .addResourceLocations("classpath:/static/js/")
        registry.addResourceHandler("/fonts/**")
            .addResourceLocations("classpath:/static/fonts/")
        registry.addResourceHandler("/css/**")
            .addResourceLocations("classpath:/static/css/")
        registry.addResourceHandler("/assets/**")
            .addResourceLocations("classpath:/static/assets/")
        registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/")
        registry.addResourceHandler("/images/**")
            .addResourceLocations("classpath:/static/images")
    }
}