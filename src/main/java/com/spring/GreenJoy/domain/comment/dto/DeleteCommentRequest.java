package com.spring.GreenJoy.domain.comment.dto;

public record DeleteCommentRequest(
        String userId,
        Long commentId
) {
}
