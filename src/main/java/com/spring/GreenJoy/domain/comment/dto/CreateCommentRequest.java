package com.spring.GreenJoy.domain.comment.dto;

public record CreateCommentRequest(
        String content,
        String randomId,
        Long postId
) {
}
