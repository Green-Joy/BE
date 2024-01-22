package com.spring.GreenJoy.domain.user;

import com.spring.GreenJoy.config.oauth.CustomOAuth2UserService;
import com.spring.GreenJoy.domain.user.dto.CreateUserRequest;
import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @PostMapping("/user")
    public String getUserProfile(@AuthenticationPrincipal OAuth2User oAuth2User) {

        CreateUserRequest req = new CreateUserRequest();

        // OAuth2User를 통해 현재 로그인한 사용자의 정보에 접근
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        // 모델에 사용자 정보 추가
        req.setName(name);
        req.setEmail(email);
        req.setRole(Role.USER);

        customOAuth2UserService.loadUser(oAuth2User.getAttribute(name));

        // 사용자 정보를 표시하는 페이지로 이동
        return "userProfile";
    }

}
