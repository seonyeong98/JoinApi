package com.example.joinapi.config;

import com.example.joinapi.service.UserTableService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {
    /*
    private String secretKey = "Secret";

    //토큰 유효시간 30분
    private long tokenValidTime = 1000L * 60 * 60;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT토큰 생성
    public String createToken (String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk); //JWT payload에 저장되는 정보 단위
        claims.put("roles", roles); //정보는 key /value 쌍으로 저장
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //정보 저장
                .setIssueAt(now) //토큰 발행시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) //만료시간설정
                .signWith(SignatureAlgorithm.HS256, secretKey) //사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
                .compact();
    }

    //Jwt 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //token에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Request의 Header에서 token값 가져옴
    public String resolveToken(HttpServletRequest request) {
        String token = null;
        Cookie cookie = WebUtils.getCookie(request, "X-AUTH-TOKEN");
        if(cookie != null) token = cookie.getValue();
        return token;
    }

    //token의 유효성 만료일자 확인
    public boolean validationToken(String jwtToken) {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(newDate());
        }
        catch (Exception e) {
            return false;
        }
    }
    */
}
