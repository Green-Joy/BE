package com.spring.GreenJoy.domain.article.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetArticleResponse {
    private String image;
    private String title;
    private String url;

    public boolean validateImage() {
        return image != null && !image.isEmpty();
    }
}
