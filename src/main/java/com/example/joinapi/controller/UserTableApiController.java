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

    private final PasswordEncoder passwordEncoder;
    private final JwtRequestFilter jwtRequestFilter;
    private final UserTableService userTableService;
    private final UserTableRepository userTableRepository;
    private final JwtTokenUtil jwtTokenUtil;
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

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody PostsResponseDto user) {
        String userId = user.getUserId();
        //UserDetails userDetails = jWtUserDetailsService.loadUserByUsername(userId);
        Optional<UserTable> member = userTableRepository.findByUserId(userId);
        if (member.isPresent()) {
            String passwd = member.get().getUserPw();
            if (!passwordEncoder.matches(user.getUserPw(), passwd)) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
            final String token = jwtTokenUtil.generateToken(userId);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            throw new IllegalArgumentException("가입되지 않은 아이디 입니다.");
        }
        //        final String token = jwtTokenUtil.generateToken(member.getUserId());
        //        return ResponseEntity.ok(new JwtResponse(token));
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> PostsResponseDto) {
//        PostsResponseDto member = userTableRepository.findByIdPw("userId")
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//        if (!passwordEncoder.matches(member.getUserPw(), member.getUserPw())) {
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//
//        final String token = jwtTokenUtil.generateToken(member.getUserId());
//        return ResponseEntity.ok(new JwtResponse(token));
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthenticationToken(
//            HttpServletRequest request, HttpServletResponse response,
//            @RequestBody JwtRequest jwtRequest) throws Exception {
//        authenticate(request, response, jwtRequest.getUserId(), jwtRequest.getUserPw());
//        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUserId());
//        final String token = jwtTokenUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(token));
//    }




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
    public String createForm(@RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        System.out.println(postsSaveRequestDto.toString());
        userTableService.createForm(postsSaveRequestDto);

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
