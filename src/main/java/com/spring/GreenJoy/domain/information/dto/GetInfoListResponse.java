package com.spring.GreenJoy.domain.information.dto;

import lombok.Builder;

@Builder
public record GetInfoListResponse(
        String title,
        String writer,
        String content,
        String thumbnail,
        Long infoId

) {
}
