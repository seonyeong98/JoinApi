package com.example.joinapi.notice.model.dto;

import com.example.joinapi.notice.domain.UserTable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PwChangeDto {
    private Integer id;
    private String userPw;

    @Builder
    public PwChangeDto(Integer id, String userPw) {
        this.id = id;
        this.userPw = userPw;

    }

    public UserTable toEntity() {
        return UserTable.builder()
                .id(id)
                .userPw(userPw)
                .build();
    }
}
