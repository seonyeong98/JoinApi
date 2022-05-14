package com.example.joinapi.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {
    private Integer id;
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
}
