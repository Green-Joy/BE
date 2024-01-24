package com.spring.GreenJoy.domain.comment.dto;

public record UpdateCommentRequest(
        String content,
        String userId
) {
}
