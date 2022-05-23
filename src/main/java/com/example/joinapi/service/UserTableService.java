package com.example.joinapi.service;

import com.example.joinapi.config.JwtTokenUtil;
import com.example.joinapi.controller.UserTableApiController;
import com.example.joinapi.domain.UserTable;
import com.example.joinapi.model.dto.PostsResponseDto;
import com.example.joinapi.model.dto.PostsSaveRequestDto;
import com.example.joinapi.model.dto.UserTableDto;
import com.example.joinapi.repository.UserTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTableService {

    private final PasswordEncoder passwordEncoder;
    private final UserTableRepository userTableRepository;
    private final JwtTokenUtil jwtTokenUtil;

//    public List<UserTableDto> existsByUserId(String userId) {
//        List<UserTableDto> list = collectionMap(userTableRepository.existsByUserId(userId), UserTableDto.class);
//        return list;
//    }

    @Transactional
    public void createForm(PostsSaveRequestDto postsSaveRequestDto) {

        String rawPassword = postsSaveRequestDto.getUserPw();
        String encPassword = passwordEncoder.encode(rawPassword);
        System.out.println("@@@@@@@@@@@@@");
        System.out.println(encPassword);
        postsSaveRequestDto.setUserPw(encPassword);
        //String a = postsSaveRequestDto.toString();
        System.out.println(postsSaveRequestDto.toString());
        userTableRepository.save(postsSaveRequestDto.toEntity());

        //UserTable saved = userTableRepository.save(postsSaveRequestDto.toEntity());
        //return saved.getId();
    }

    @Transactional
    public void updateForm(UserTableDto user) {
        System.out.println(user.toString());
        Integer id = user.getId();
        Optional<UserTable> entity = userTableRepository.findById(String.valueOf(id));
        if(entity.isPresent()) {
            UserTable selectEntity = entity.get();
            selectEntity.setUserId(user.getUserId());
            selectEntity.setUserPw(user.getUserPw());
            selectEntity.setEmail(user.getEmail());
            selectEntity.setBirth(user.getBirth());
            selectEntity.setGender(user.getGender());
            selectEntity.setName(user.getName());
            selectEntity.setPnu(user.getPnu());
            selectEntity.setJoinDt(user.getJoinDt());
            selectEntity.setLastLoginDt(user.getLastLoginDt());
            selectEntity.setUpdateDt(user.getUpdateDt());
            userTableRepository.save(selectEntity);
        } else {
            System.out.println("데이터가 존재하지 않습니다, id: " + id);
        }
    }

    @Transactional
    public void deleteForm(Integer id) {
        System.out.println(id);
        Optional<UserTable> entity = userTableRepository.findById(String.valueOf(id));
        if(entity.isPresent()) {
            userTableRepository.delete(entity.get());
        } else {
            System.out.println("데이터가 존재하지 않습니다." + id);
        }
    }

    public String getToken(PostsResponseDto user) {
        String userId = user.getUserId();
        Boolean result = this.isPasswordMatch(user);
        if (result) {
            return jwtTokenUtil.generateToken(userId);
        } else {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
    }


    public Boolean isPasswordMatch(PostsResponseDto user) {
        String userId = user.getUserId();
        //UserDetails userDetails = jWtUserDetailsService.loadUserByUsername(userId);
        Optional<UserTable> member = userTableRepository.findByUserId(userId);
        if (member.isPresent()) {
            String passwd = member.get().getUserPw();
            return passwordEncoder.matches(user.getUserPw(), passwd);
        } else {
            throw new IllegalArgumentException("가입되지 않은 아이디 입니다.");
        }
    }





    //중복 Id 체크
//    @Transactional
//    public UserTableDto.Response userTable(UserTableDto.Request request) {
//        if(userTableRepository.findById(request.getUserId()).isPresent() {
//        User user = modelMapper.map(request, User.class);
//            user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//            return modelMapper.map(userTableRepository.save(user), UserTableDto.Response.class);
//        }


    public boolean isExistsUserId(String userId) {
        return userTableRepository.existsByUserId(userId);
    }

    public boolean isExistsEmail(String email) {
        return userTableRepository.existsByEmail(email);
    }

//사용자 아이디 존재여부
//public boolean isExistsUserId(String userId) {
//    boolean isMemberExist = userTableRepository.existsByUserId(userId);
//    boolean isMemberAccountsExist = userTableService.isExistsLoginId(userId);
//    if(isMemberExist || isMemberAccountsExist) {
//        return true;
//    } else {
//        return false;
//    }
//}
//
//    public boolean isExistsLoginId(String loginId) {
//        return
//    }


}
