package com.example.joinapi.notice.controller;

import com.example.joinapi.file.domain.UploadFile;
import com.example.joinapi.file.model.dto.FileResponseDto;
import com.example.joinapi.login.jwt.config.JwtRequestFilter;
import com.example.joinapi.login.jwt.service.JwtUserDetailsService;
import com.example.joinapi.notice.model.dto.*;
import com.example.joinapi.notice.repository.UserTableRepository;
import com.example.joinapi.file.service.FileUploadService;
import com.example.joinapi.notice.service.UserTableService;
import com.example.joinapi.file.utils.DownloadUtil;
import com.example.joinapi.file.utils.UploadUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@ResponseBody
@RestController
@ToString
@RequestMapping("/api")
public class UserTableApiController {
    //컨트롤러에서 서비스를 부르고 서비스에서 레포지터리 불러오기

    @Value("${file.path}")
    private String file_path;



    private final JwtRequestFilter jwtRequestFilter;
    private final UserTableService userTableService;
    private final UserTableRepository userTableRepository;
    private final JwtUserDetailsService jWtUserDetailsService;
    private final FileUploadService fileUploadService;



    @GetMapping("/info/{id}")
    public void getInfo(@PathVariable Integer id,@RequestBody GetInfoDto getInfoDto) throws Exception{
        userTableService.getInfo(id, getInfoDto);
    }

    @PatchMapping("/update/{id}")
    //@JsonProperty("postUpdateRequestDto")
    //JSON형태로 받아온 데이터를 @RequestBody를 통해 PostUpdateRequestDto 객체로 바꿔준다
    public Map<String, Object> update(@PathVariable Integer id, @RequestBody PostUpdateRequestDto postUpdateRequestDto) throws Exception  {
        //서비스 -> entity
        System.out.println(ToStringBuilder.reflectionToString(postUpdateRequestDto));
        return userTableService.update(id, postUpdateRequestDto);
    }

    @PostMapping("/join")
    public String createForm(@RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        userTableService.createForm(postsSaveRequestDto);
        return "saved";
    }


    /**비밀번호 확인API*/
    @PostMapping("/checkPw")
    @JsonProperty("pwChangeDto")
    public ResponseEntity<ResponseResult> passwordCheck2(@RequestBody PwChangeDto pwChangeDto) {
        return ResponseEntity.ok(ResponseResult.of(userTableService.passwordCheck2(pwChangeDto)));
    }

    /**비밀번호 변경API*/
    @PatchMapping("/changePw")
    public String modifyPw(@RequestBody PwChangeDto pwChangeDto) {
        userTableService.modifyPw(pwChangeDto);
        return "changeSuccess";
    }

    //@Transactional
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody PostsResponseDto user) {
        String token = userTableService.getToken(user);
        SelectUserInfoDto info = userTableService.findByUserId(user.getUserId());
        return ResponseEntity.ok(new UserTableApiController.JwtResponse(token, info));
    }
    @DeleteMapping("/delete/{id}")
    public String deleteForm(@PathVariable("id") Integer id) {
        System.out.println(id);
        userTableService.deleteForm(id);

        return "deleted";
    }

    /**
     * id: 유저테이블의 키 아이디
     * @param id
     */
    @DeleteMapping("/remove/{id}")
    public void remove (@PathVariable Integer id) {
        //fileUploadService.delete(id);
        userTableService.deleteFile(id);
    }

    @Data
    class JwtRequest {

        private String userId;
        private String userPw;

    }

    @Data
    @AllArgsConstructor
    class JwtResponse {

        private String token;
        private SelectUserInfoDto info;

    }

}
