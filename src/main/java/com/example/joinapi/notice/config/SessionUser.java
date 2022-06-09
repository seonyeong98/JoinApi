package com.example.joinapi.notice.config;

import com.example.joinapi.notice.domain.UserTable;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.enabled;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class SessionUser  {

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

    public static SessionUser of(UserTable user) {
        return new SessionUser(
                user.getId(), user.getUserId(), user.getUserPw(),
                user.getName(), user.getEmail(), user.getPnu(),
                user.getGender(), user.getBirth(), user.getJoinDt(),
                user.getLastLoginDt(), user.getUpdateDt(),user.getFileId());
    }

    @Builder
    public static SessionUser of(Integer id, String userId, String userPw, String name, String email, String pnu,
                                 String gender, LocalDate birth, LocalDateTime joinDt, LocalDateTime updateDt, LocalDateTime lastLoginDt, Integer fileId) {
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(id);
        sessionUser.setUserId(userId);
        sessionUser.setUserPw(userPw);
        sessionUser.setName(name);
        sessionUser.setEmail(email);
        sessionUser.setPnu(pnu);
        sessionUser.setGender(gender);
        sessionUser.setBirth(birth);
        sessionUser.setJoinDt(joinDt);
        sessionUser.setUpdateDt(updateDt);
        sessionUser.setLastLoginDt(lastLoginDt);
        sessionUser.setFileId(fileId);
        return sessionUser;
    }

    public SessionUser(UserTable userTable) {
        this.name = userTable.getName();
    }


    public String getUsername() {
        return this.userId;
    }


    public String getPassword() {
        return this.userPw;
    }


    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return enabled;
    }

}


