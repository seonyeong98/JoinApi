package com.example.joinapi.model.dto;

import com.example.joinapi.config.Authority;
import com.example.joinapi.domain.UserTable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class UserTableDto {



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

    private Collection<SimpleGrantedAuthority> authorities;


    public UserTableDto(Integer id, String userId, String userPw, String name, String email, String pnu, String gender, LocalDate birth, LocalDateTime joinDt, LocalDateTime lastLoginDt, LocalDateTime updateDt) {
        this.id = id;
        this.userId = userId;
        this.userPw = userPw;
        this.name = name;
        this.email = email;
        this.pnu = pnu;
        this.gender = gender;
        this.birth = birth;
        this.joinDt = joinDt;
        this.lastLoginDt = lastLoginDt;
        this.updateDt = updateDt;
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
                .lastLoginDt(lastLoginDt)
                .updateDt(updateDt)
                .build();
    }


}




