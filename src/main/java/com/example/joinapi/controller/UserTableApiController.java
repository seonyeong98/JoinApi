package com.example.joinapi.controller;

import com.example.joinapi.config.*;
import com.example.joinapi.domain.UserTable;
import com.example.joinapi.model.dto.PostsResponseDto;
import com.example.joinapi.model.dto.PostsSaveRequestDto;
import com.example.joinapi.model.dto.ResponseResult;
import com.example.joinapi.model.dto.UserTableDto;
import com.example.joinapi.repository.UserTableRepository;
import com.example.joinapi.service.UserTableService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@ResponseBody
@RestController
@ToString
@RequestMapping("/api")
public class UserTableApiController {
    //컨트롤러에서 서비스를 부르고 서비스에서 레포지터리 불러오기


    private final JwtRequestFilter jwtRequestFilter;
    private final UserTableService userTableService;
    private final UserTableRepository userTableRepository;
    private final JwtUserDetailsService jWtUserDetailsService;



    //중복체크 API
    @GetMapping("/userid-exist/{userId}")
        public ResponseEntity<ResponseResult> idCheck(@PathVariable String userId) {
            return ResponseEntity.ok(ResponseResult.of(userTableService.isExistsUserId(userId)));
        }

    @GetMapping("/user-emails/{email}")
        public ResponseEntity<ResponseResult> emailCheck(@PathVariable String email) {
        return ResponseEntity.ok(ResponseResult.of(userTableService.isExistsEmail(email)));
    }

    @PostMapping("/join")
    public String createForm(@RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        userTableService.createForm(postsSaveRequestDto);
        return "saved";

    }


    @Transactional
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody PostsResponseDto user) {
        String token = userTableService.getToken(user);
        return ResponseEntity.ok(new UserTableApiController.JwtResponse(token));
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response){
        Cookie cookie = new Cookie("X-AUTH-TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /*
    @GetMapping("/info")
    public UserTableDto getInfo(){
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (details != null && !(details instanceof String)) return new UserTableDto((User) details);
        return null;
    }
    */


    @GetMapping
    public ResponseEntity<List<UserTable>> findAll() {
        List<UserTable> entity = userTableRepository.findAll();
        return ResponseEntity.ok(entity);
    }



    @PatchMapping("/update")
    public String updateForm(@RequestBody UserTableDto user) {
        System.out.println(user.toString());
        userTableService.updateForm(user);
        return "saved";
    }


//    @PatchMapping("/info-update")
//    public ResponseEntity updateMyInfo(@Valid @RequestBody MyInfoUpdateRequestDto updateRequest) throws RuntimeException {
//        memberService.updateMyInfo(updateRequest);
//
//        return ResponseEntity.noContent().build();
//    }
//    @DeleteMapping("/delete/{id}")
//    public String deleteForm(@PathVariable("id") Integer id) {
//        System.out.println(id);
//        userTableService.deleteForm(id);
//
//        return "deleted";
//    }


    @Data
    class JwtRequest {

        private String userId;
        private String userPw;

    }

    @Data
    @AllArgsConstructor
    class JwtResponse {

        private String token;

    }


}
