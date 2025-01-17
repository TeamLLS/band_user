package com.example.band_authentication.user;


import com.example.band_authentication.external.oauth.UserInfo;
import com.example.band_authentication.user.form.UserInfoChangeForm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;
    private String email;

    @NotNull
    private String name;
    private String gender;
    private Integer birthYear;
    private String phNum;

    private String description;
    private String image;

    private Instant createdAt;

    public User(UserInfo userInfo) {
        this.username = userInfo.getProvider() + "_" + userInfo.getId();
        this.email = userInfo.getEmail();
        this.name = userInfo.getName();
        this.gender = userInfo.getGender();
        this.phNum = userInfo.getPhone_number();
        this.birthYear = userInfo.getBirthyear();
        this.description = null;
        this.image = "common/profile/default.png";
        this.createdAt = Instant.now();
    }

    public void update(UserInfoChangeForm changeForm){

        if(changeForm.isImageChanged()){
            this.image = changeForm.getImageResource();
        }
        if(changeForm.isDescriptionChanged()){
            this.description=changeForm.getDescription();
        }
        if(changeForm.isEmailChanged()){
            this.email=changeForm.getEmail();
        }
        if(changeForm.isPhNumChanged()){
            this.phNum= changeForm.getPhNum();
        }
    }
}
