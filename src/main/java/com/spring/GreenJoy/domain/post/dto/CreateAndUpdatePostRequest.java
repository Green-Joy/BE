package com.spring.GreenJoy.domain.post.dto;

public record CreateAndUpdatePostRequest(
        String title,
        String userId,
        String content,
        String image1,
        String image2,
        String image3
) {
}
