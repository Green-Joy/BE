package com.spring.GreenJoy.domain.user.dto;

import com.spring.GreenJoy.global.common.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserRequest {
    private String id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String profileImg;
    private int credit;
    private Role role;
}
