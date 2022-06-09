package com.example.joinapi.notice.service;

import com.example.joinapi.login.jwt.config.JwtTokenUtil;
import com.example.joinapi.file.domain.UploadFile;
import com.example.joinapi.notice.domain.UserTable;
import com.example.joinapi.file.model.dto.PostFileUploadDto;
import com.example.joinapi.file.repository.UploadFileRepository;
import com.example.joinapi.notice.model.dto.*;
import com.example.joinapi.notice.repository.UserTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTableService {

    private final PasswordEncoder passwordEncoder;
    private final UserTableRepository userTableRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UploadFileRepository uploadFileRepository;

//    public List<UserTableDto> existsByUserId(String userId) {
//        List<UserTableDto> list = collectionMap(userTableRepository.existsByUserId(userId), UserTableDto.class);
//        return list;
//    }

    @Transactional
    public void getInfo(Integer id, GetInfoDto getInfoDto) {
        UserTable userTable = userTableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다." + id));
    }

    @Transactional
    public void createForm(PostsSaveRequestDto postsSaveRequestDto) {
        String rawPassword = postsSaveRequestDto.getUserPw();
        String encPassword = passwordEncoder.encode(rawPassword);
        postsSaveRequestDto.setUserPw(encPassword);
        PostFileUploadDto file = postsSaveRequestDto.getFileInfo();
        if (file != null) {
            Integer fileId = uploadFileRepository.save(file.toEntity()).getId();
            //System.out.println(ToStringBuilder.reflectionToString(file));
            System.out.println(fileId);
            postsSaveRequestDto.setFileId(fileId);
        }
        //String a = postsSaveRequestDto.toString();
        System.out.println(postsSaveRequestDto.toString());
        userTableRepository.save(postsSaveRequestDto.toEntity());
        //UserTable saved = userTableRepository.save(postsSaveRequestDto.toEntity());
        //return saved.getId();
    }

    @Transactional
    public Map<String, Object> update(Integer id, PostUpdateRequestDto postUpdateRequestDto) throws Exception {
        UserTable userTable = userTableRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다." + id));
        PostFileUploadDto file = postUpdateRequestDto.getFileInfo();
        Integer fileId = null;
        if (file != null) {
            fileId = uploadFileRepository.save(file.toEntity()).getId();
            //System.out.println(ToStringBuilder.reflectionToString(file));
            System.out.println(fileId);
        }
        System.out.println(postUpdateRequestDto.toString());
        userTable.update(
                postUpdateRequestDto.getName(),
                postUpdateRequestDto.getEmail(),
                postUpdateRequestDto.getPnu(),
                postUpdateRequestDto.getGender(),
                postUpdateRequestDto.getBirth(),
                fileId);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userKey", id);
        resultMap.put("fileId", fileId);
        return resultMap;
    }


    @Transactional
    public void deleteForm(Integer id) {
        System.out.println(id);
        Optional<UserTable> entity = userTableRepository.findById(id);
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

    public SelectUserInfoDto findByUserId(String userId) {
        Optional<UserTable> userInfo = userTableRepository.findByUserId(userId);
        return new SelectUserInfoDto(userInfo.get());
    }


    public Boolean passwordCheck(Integer id, String userPw) {
        Optional<UserTable> userTable = userTableRepository.findById(id);
        if (userTable.isPresent()) {
            String passwd = userTable.get().getUserPw();
            return passwordEncoder.matches(userPw, passwd);
        } else {
            throw new IllegalArgumentException("가입되지 않은 아이디 입니다.");
        }
    }

    public Boolean passwordCheck2(PwChangeDto pwChangeDto) {
        Optional<UserTable> userTable = userTableRepository.findById(pwChangeDto.getId());
        if (userTable.isPresent()) {
            String passwd = userTable.get().getUserPw();
            return passwordEncoder.matches(pwChangeDto.getUserPw(), passwd);
        } else {
            throw new IllegalArgumentException("가입되지 않은 아이디 입니다.");
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

    @Transactional
    public void modifyPw(PwChangeDto pwChangeDto) {
        Optional<UserTable> userTable = userTableRepository.findById(pwChangeDto.getId());
        if(userTable.isPresent()){
            String rawPassword = pwChangeDto.getUserPw();
            String encPassword = passwordEncoder.encode(rawPassword);
            System.out.println(encPassword);
            userTable.get().changePasswd(encPassword);
            //userTable.get().setUserPw(encPassword);//도메인 클래스(UserTable)에 바로 set해주는 건 좋지 않음, 에러 났을 때 어디서 에러난 지 모를 수 있음

            /*
            pwChangeDto.setUserPw(encPassword);
            userTableRepository.save(pwChangeDto.toEntity());
            전체 entity가 save되어 입력해주지 않은 값엔 null발생
            */

        } else {
            throw new IllegalArgumentException("가입되지 않은 아이디 입니다.");
        }
    }

    @Transactional
    public void deleteFile(Integer id) {
        Optional<UserTable> optional = userTableRepository.findById(id);
        if (optional.isPresent()) {
            UserTable userTable = optional.get();
            Integer fileId = userTable.getFileId();
            this.deleteFile2(fileId);
            userTable.deleteFile();
        }
    }

    public void deleteFile2(Integer id) {
        UploadFile entity = uploadFileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (entity.getId().equals(id)) {
            java.io.File file = new java.io.File(entity.getFilePath() + entity.getFileName());
            if(file.exists()) {
                file.delete();
            }
            uploadFileRepository.delete(entity);
        }
    }

}
