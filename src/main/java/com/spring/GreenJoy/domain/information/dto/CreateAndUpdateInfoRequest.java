package com.spring.GreenJoy.domain.information.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateAndUpdateInfoRequest(
        String title,
        String randomId,
        String content,
        List<MultipartFile> images
) {
}
