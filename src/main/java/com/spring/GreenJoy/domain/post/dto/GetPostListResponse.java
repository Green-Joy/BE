package com.spring.GreenJoy.domain.post.dto;

import lombok.Builder;

@Builder
public record GetPostListResponse(
        String title,
        String writer,
        String content,
        String thumbnail,
        Long likeCount,
        Long postId,
        String profileImg

) {
}
