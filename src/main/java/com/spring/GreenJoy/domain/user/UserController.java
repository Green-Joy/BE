package com.spring.GreenJoy.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.GreenJoy.domain.user.dto.GetUserInfoResponse;
import com.spring.GreenJoy.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String authCode) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(authCode);
        String extractedAuthCode = jsonNode.get("authCode").asText();

        User user = userService.verify(extractedAuthCode);

        return ResponseEntity.ok().body(user.getRandomId());
    }

    @GetMapping("/{randomId}")
    public ResponseEntity<?> getUserInfo(@PathVariable("randomId") String randomId) {

        GetUserInfoResponse user = userService.getUserInfo(randomId);

        return ResponseEntity.ok().body(user);
    }

}
