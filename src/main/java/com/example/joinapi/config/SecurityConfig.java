
package com.example.joinapi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private JwtUserDetailService jwtUserDetailService;
//    private JwtRequestFilter jwtRequestFilter;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    SecurityConfig(JwtUserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
//        this.jwtUserDetailService = jwtUserDetailsService;
//        this.jwtRequestFilter = jwtRequestFilter;
//        this.passwordEncoder = passwordEncoder;
//    }
//    @Autowired
//    private JwtAuthenticationProvider jwtAuthenticationProvider

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() //교차출처 리소스 공유
                .and()
                .csrf().disable() //사이트간 요청 위조 설정 복잡해서 disable해줌
                .exceptionHandling() //인증, 허가 에러 시 공통적으로 처리
                .and()
                .sessionManagement() //JWT를 쓰려면 SpringSecurity에서 기본적으로 지원하는 Session설정을 해제
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() //요청에 따른 권한 체크 설정 부분 /auth/** 경로로 들어오는 부분은 인증이 필요없고, 그 외 모든 요청들은 인증을 거치도록 설정
                .antMatchers("/", "/login", "/api/join").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable().headers().frameOptions().disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}