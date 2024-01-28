package com.spring.GreenJoy.domain.information.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetInfoResponse(
        String title,
        String writer,
        String content,
        LocalDateTime updatedAt,
        String image1,
        String image2,
        String image3
) {
}
