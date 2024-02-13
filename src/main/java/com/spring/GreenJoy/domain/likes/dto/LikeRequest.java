package com.spring.GreenJoy.domain.likes.dto;

import lombok.Builder;

@Builder
public record LikeRequest(
        String randomId,
        Long postId
) {
}
