package com.example.joinapi.notice.model.dto;

import com.example.joinapi.notice.domain.UserTable;
import com.example.joinapi.file.model.dto.PostFileUploadDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostsSaveRequestDto {
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
    private Integer fileId;
    private PostFileUploadDto fileInfo;

    @Builder
    public PostsSaveRequestDto(UserTable entity) {
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
        this.fileId = entity.getFileId();
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
                .joinDt(LocalDateTime.now())
                .lastLoginDt(LocalDateTime.now())
                .updateDt(LocalDateTime.now())
                .fileId(fileId)
                .build();

    }


}

