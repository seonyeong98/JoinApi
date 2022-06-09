package com.example.joinapi.notice.model.dto;

import com.example.joinapi.notice.domain.UserTable;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SelectUserInfoDto {
    private Integer id;
    private String userId;
    private String userPw;
    private String name;
    private LocalDate birth;
    private String gender;
    private String email;
    private String pnu;
    private Integer fileId;

    public SelectUserInfoDto(UserTable entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.userPw = entity.getUserPw();
        this.name = entity.getName();
        this.birth = entity.getBirth();
        this.gender = entity.getGender();
        this.email = entity.getEmail();
        this.pnu = entity.getPnu();
        this.fileId = entity.getFileId();
    }
}

