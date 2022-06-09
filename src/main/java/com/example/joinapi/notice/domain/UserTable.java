package com.example.joinapi.notice.domain;

import com.example.joinapi.notice.model.dto.PostUpdateRequestDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="usertable")
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "pnu")
    private String pnu;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth")
    private LocalDate birth;

    @CreationTimestamp //INSERT 시 자동으로 값을 채워줌
    @Column(name = "join_dt")
    private LocalDateTime joinDt;

    @Column(name = "last_login_dt")
    private LocalDateTime lastLoginDt;

    @UpdateTimestamp
    @Column(name = "update_dt")
    private LocalDateTime updateDt;

    @Column
    private Integer fileId;


    @Override
    public int hashCode() {
        return Objects.hash(id, userId, userPw, name, email, pnu, gender, birth, joinDt, updateDt, lastLoginDt);
    }
    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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

    public void update(String name, String email, String pnu, String gender,
                       LocalDate birth, Integer fileId) {
        System.out.println(this.userId);
        if (email != null) {
            this.email = email;
        }
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.pnu = pnu;
        if (fileId != null) {
            this.fileId = fileId;
        }
    }

    //password만 바꾸고 싶을 때
    public void changePasswd(String newPasswd) {
        this.userPw = newPasswd;
    }

    public void update2(PostUpdateRequestDto postUpdateRequestDto) {
        System.out.println(this.userId);
        this.name = postUpdateRequestDto.getName();
        /*
        this.birth = birth;
        this.gender = gender;
        this.email = email;
        this.pnu = pnu;
        */
    }

    public void deleteFile() {
        this.fileId = null;
    }

}

