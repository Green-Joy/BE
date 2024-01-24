package com.spring.GreenJoy.config.oauth;

import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.NanoId;
import com.spring.GreenJoy.global.common.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String profileImg;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String profileImg) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
    }

    // OAuth2User에서 반환하는 사용자 정보는 Map
    // 따라서 값 하나하나를 변환해야 한다.
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {

        return ofGoogle(userNameAttributeName, attributes);
    }

    // 구글 생성자
    private static OAuthAttributes ofGoogle(String usernameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profileImg((String) attributes.get("profileImg"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    // User 엔티티 생성
    public User toEntity(String provider, String providerId) {
        return User.builder()
                .userId(NanoId.makeId())
                .email(email)
                .name(name)
                .nickname("TEMPNICKNAME")
                .provider(provider)
                .providerId(providerId)
                .profileImg(profileImg)
                .role(Role.USER)
                .build();
    }

}