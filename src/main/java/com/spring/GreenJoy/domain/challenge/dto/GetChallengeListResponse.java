package com.spring.GreenJoy.domain.challenge.dto;

import lombok.Builder;

@Builder
public record GetChallengeListResponse(
        String title,
        // String writer,
        // String content,
        String thumbnail,
        String challengeDate
) {
}
