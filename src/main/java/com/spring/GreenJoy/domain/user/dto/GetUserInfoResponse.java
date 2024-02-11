package com.spring.GreenJoy.domain.user.dto;

import lombok.Builder;

@Builder
public record GetUserInfoResponse(
        String name,
        String profileImg,
        String email
) {
}
