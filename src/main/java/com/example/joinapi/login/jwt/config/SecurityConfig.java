
package com.example.joinapi.login.jwt.config;

import com.example.joinapi.login.jwt.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtRequestFilter jwtRequestFilter;

    //@Autowired
    //private final JwtAuthenticationEntryPoint unauthorizedHandler;

    // AuthenticationManagerBuilder의 passwordEncoder()를 통해
    // 패스워드 암호화에 사용될 PasswordEncoder 구현체를 지정할 수 있습니다.
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



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
                .antMatchers("/", "/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable().headers().frameOptions().disable();
    }

    //비밀번호 암호화를 위한 Encoder설정
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}