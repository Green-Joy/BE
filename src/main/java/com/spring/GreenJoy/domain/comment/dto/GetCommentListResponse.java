package com.spring.GreenJoy.domain.comment.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetCommentListResponse(
        String content,
        String writer,
        LocalDateTime updatedAt,
        Long commentId
) {
}
