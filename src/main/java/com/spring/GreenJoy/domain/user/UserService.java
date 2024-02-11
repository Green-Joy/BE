package com.spring.GreenJoy.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.GreenJoy.domain.user.dto.GetUserInfoResponse;
import com.spring.GreenJoy.domain.user.dto.OAuthResponse;
import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.NanoId;
import com.spring.GreenJoy.global.common.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUrl;
    private static final String GOOGLE_TOKEN_SERVER_URL = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USERINFO_SERVER_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

    @Transactional
    public User verify(String authCode) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUrl);
        params.add("code", authCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<OAuthResponse> response = restTemplate.postForEntity(GOOGLE_TOKEN_SERVER_URL, request, OAuthResponse.class);
        System.out.println(response.getBody());

        String accessToken = response.getBody().getAccess_token();

        User user = null;
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GOOGLE_USERINFO_SERVER_URL)
                    .queryParam("access_token", accessToken);
            user = restTemplate.getForObject(builder.toUriString(), User.class);

            // 데이터베이스에서 사용자 조회
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                existingUser.get().setRandomId(NanoId.makeId());
                userRepository.save(existingUser.get());
            } else {
                user.setUserId(NanoId.makeId());
                user.setRandomId(NanoId.makeId());
                user.setRole(Role.USER);
                userRepository.save(user);
            }

            System.out.println(user.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid access token", e);
        }

        return user;
    }

    public GetUserInfoResponse getUserInfo(String randomId) {
        User user = userRepository.findByRandomId(NanoId.of(randomId))
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));


        return GetUserInfoResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profileImg(user.getProfileImg())
                .build();
    }

}
