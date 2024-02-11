package com.spring.GreenJoy.domain.post.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateAndUpdatePostRequest(
        String title,
        String randomId,
        String content,
        List<MultipartFile> images
) {
}
