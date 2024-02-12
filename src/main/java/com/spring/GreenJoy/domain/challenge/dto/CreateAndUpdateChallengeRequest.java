package com.spring.GreenJoy.domain.challenge.dto;

import org.springframework.web.multipart.MultipartFile;

public record CreateAndUpdateChallengeRequest(
        String title,
        String randomId,
        String content,
        MultipartFile image
) {
}
