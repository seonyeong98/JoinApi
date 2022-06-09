package com.example.joinapi.notice.model.dto;

import com.example.joinapi.notice.domain.UserTable;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public class PostsResponseDto {
    private Integer id;
    private String userId;
    private String userPw;
    private String name;
    private String email;
    private String pnu;
    private String gender;
    private LocalDate birth;
    private LocalDateTime joinDt;
    private LocalDateTime lastLoginDt;
    private LocalDateTime updateDt;

    /*
    빈생성자 (@NoArgsConstructor)
    public PostsResponseDto() {
    }
    */

    @Builder
    public PostsResponseDto(UserTable entity) {
        this.id = entity.getId();
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
                .id(id)
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

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, userPw, name, email, pnu, gender, birth, joinDt, updateDt, lastLoginDt);
    }

    public Integer getId() {
        return id;
    }

    public String getUserPw() {
        return userPw;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPnu() {
        return pnu;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public LocalDateTime getJoinDt() {
        return joinDt;
    }

    public LocalDateTime getUpdateDt() {
        return updateDt;
    }

    public LocalDateTime getLastLoginDt() {
        return lastLoginDt;
    }

}

