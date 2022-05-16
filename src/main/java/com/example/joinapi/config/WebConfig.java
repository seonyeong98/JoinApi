package com.example.joinapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //어떤 경우를 허락해줄것인지 /** 모든 경로 허용
                .allowedOrigins("http://localhost:3000") //localhost3000번은 다른 도메인이지만 허락해주겠다
                .allowedHeaders("*")
                .allowedMethods("*");
        }

}
