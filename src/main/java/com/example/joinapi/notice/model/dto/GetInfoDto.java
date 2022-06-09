package com.example.joinapi.notice.model.dto;

import lombok.Getter;

import java.time.LocalDate;
@Getter
public class GetInfoDto {
    private String name;
    private LocalDate birth;
    private String gender;
    private String email;
    private String pnu;
    private Integer fileId;
}
