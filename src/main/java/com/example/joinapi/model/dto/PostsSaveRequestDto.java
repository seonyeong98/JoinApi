package com.example.joinapi.model.dto;

import com.example.joinapi.domain.UserTable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String userId;
    private String userPw;
    private String name;
    private String email;
    private String pnu;
    private String gender;
    private LocalDateTime birth;
    private LocalDateTime joinDt;
    private LocalDateTime lastLoginDt;
    private LocalDateTime updateDt;

    @Builder
    public PostsSaveRequestDto(UserTable entity) {
        this.userId = entity.getUserId();
        this.userPw = entity.getUserPw();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.pnu = entity.getPnu();
        this.gender = entity.getGender();
        this.birth = entity.getBirth();
        this.joinDt = entity.getJoinDt();
        this.lastLoginDt = entity.getLastLoginDt();
        this.updateDt = entity.getUpdateDt();
    }

    public UserTable toEntity() {
        return UserTable.builder()
                .userId(userId)
                .userPw(userPw)
                .name(name)
                .email(email)
                .pnu(pnu)
                .gender(gender)
                .birth(birth)
                .joinDt(joinDt)
                .lastLoginDt(LocalDateTime.now())
                .updateDt(LocalDateTime.now())
                .build();

    }
}

