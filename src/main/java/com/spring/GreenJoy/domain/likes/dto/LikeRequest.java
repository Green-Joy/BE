package com.spring.GreenJoy.domain.likes.dto;

import lombok.Builder;

@Builder
public record LikeRequest(
        String userId,
        Long postId
) {
}
