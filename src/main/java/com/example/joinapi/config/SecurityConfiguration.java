package com.example.joinapi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private final JwtAthenticationEntryPoint unuthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() //교차출처 리소스 공유
                .and()
                .csrf().disable() //사이트간 요청 위조 설정 복잡해서 disable해줌
                .exceptionHandling() //인증, 허가 에러 시 공통적으로 처리
                .authenticationEntryPoint(unuthorizedHandler)
                .and()
                .sessionManagement() //JWT를 쓰려면 SpringSecurity에서 기본적으로 지원하는 Session설정을 해제
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() //요청에 따른 권한 체크 설정 부분 /auth/** 경로로 들어오는 부분은 인증이 필요없고, 그 외 모든 요청들은 인증을 거치도록 설정
                .antMatchers(RestControllerBase.API_URI_PREFIX + "/auth/**")
                .permitAll()
                .andMatchers(RestControllerBase.API_URI_PREFIX + "/**").authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable().headers().frameOptions().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
