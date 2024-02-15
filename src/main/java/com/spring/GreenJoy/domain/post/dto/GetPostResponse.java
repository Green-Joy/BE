package com.spring.GreenJoy.domain.post.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetPostResponse(
        String title,
        String writer,
        String content,
        LocalDateTime updatedAt,
        String image1,
        String image2,
        String image3,
        Long likeCount
) {
}
