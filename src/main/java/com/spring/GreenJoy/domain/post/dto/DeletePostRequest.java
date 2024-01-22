package com.spring.GreenJoy.domain.post.dto;

public record DeletePostRequest(
        String userId,
        Long postId
) {
}
