package com.spring.GreenJoy.domain.user.dto;

import com.spring.GreenJoy.domain.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 직렬화 기능을 가진 세션 DTO
 */
@Getter
public class SessionUser implements Serializable {

    // 인증된 사용자 정보만 필요 => name, email, profileImg 필드만 선언
    private String name;
    private String email;
    private String profileImg;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImg = user.getProfileImg();
    }

}
