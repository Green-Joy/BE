package com.spring.GreenJoy.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OAuthResponse {
    private String access_token;
    private String id_token;

}

