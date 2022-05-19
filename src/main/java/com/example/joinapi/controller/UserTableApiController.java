package com.example.joinapi.controller;

import com.example.joinapi.config.JwtRequestFilter;
import com.example.joinapi.config.SessionUser;
import com.example.joinapi.domain.UserTable;
import com.example.joinapi.model.dto.ResponseResult;
import com.example.joinapi.model.dto.UserTableDto;
import com.example.joinapi.repository.UserTableRepository;
import com.example.joinapi.service.UserTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@ResponseBody
@RestController
@RequestMapping("/api")
public class UserTableApiController {
    //컨트롤러에서 서비스를 부르고 서비스에서 레포지터리 불러오기

    private final PasswordEncoder passwordEncoder;
    private final JwtRequestFilter jwtRequestFilter;
    private final UserTableService userTableService;
    private final UserTableRepository userTableRepository;


    //중복체크 API
    @GetMapping("userid-exist/{userId}")
        public ResponseEntity<ResponseResult> idCheck(@PathVariable String userId) {
            return ResponseEntity.ok(ResponseResult.of(userTableService.isExistsUserId(userId)));
        }

    @GetMapping("user-emails/{email}")
        public ResponseEntity<ResponseResult> emailCheck(@PathVariable String email) {
        return ResponseEntity.ok(ResponseResult.of(userTableService.isExistsEmail(email)));
    }


    @PostMapping("/join2")
    public void join(@RequestBody UserTableDto dto) {
        UserTable user = UserTable.builder()
                .userId(dto.getUserId())
                .userPw(dto.getUserPw())
                .name(dto.getName())
                .email(dto.getEmail())
                .pnu(dto.getPnu())
                .gender(dto.getGender())
                .birth(dto.getBirth())
                //.roles(Collections.singletonList("ROLE_USER"))
                .build();
        userTableService.createForm(user);
    }


    @PostMapping("/login")
    public String login(@RequestBody UserTable user) {
        User member = userTableRepository.findByUserId(user.getUserId("userId"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다."); }
        return jwtRequestFilter.createToken(member.getuserId(), member.getRoles());
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
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserTable> findById(@PathVariable Integer id) {
//        Optional<UserTable> entity = userTableRepository.findById(id);
//        return ResponseEntity.ok(entity.get());
//    }
//
//    @GetMapping("/posts/save")
//    public String postsSave(){
//        return "save";
//    }
//
    @PostMapping("/join")
    public String createForm(@RequestBody UserTable user) {
        System.out.println(user.toString());
        userTableService.createForm(user);

        return "saved";
    }
//
//    @PatchMapping("/update")
//    public String updateForm(@RequestBody UserTableDto user) {
//        System.out.println(user.toString());
//        userTableService.updateForm(user);
//        return "saved";
//    }
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



}
