package com.spring.GreenJoy.domain.challenge;

import com.spring.GreenJoy.domain.challenge.dto.CreateAndUpdateChallengeRequest;
import com.spring.GreenJoy.domain.challenge.dto.GetChallengeListResponse;
import com.spring.GreenJoy.domain.challenge.dto.GetChallengeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/api/challenge")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

//    @GetMapping("/today")
//    public ResponseEntity<?> createTodayChallenge() {
//        String todayChallenge = challengeService.createTodayChallenge();
//        return ResponseEntity.ok().body(todayChallenge.getBytes(StandardCharsets.UTF_8));
//    }

    @GetMapping("/today")
    public ResponseEntity<?> createTodayChallenge() {
        String todayChallenge = challengeService.getTodayChallenge();
        return ResponseEntity.ok().body(todayChallenge.getBytes(StandardCharsets.UTF_8));
    }

    @PostMapping
    public ResponseEntity<?> certifyChallenge(@ModelAttribute CreateAndUpdateChallengeRequest createAndUpdateChallengeRequest) throws IOException {
        String todayChallenge = challengeService.certifyChallenge(createAndUpdateChallengeRequest);
        return ResponseEntity.ok().body("챌린지 업로드 성공".getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping
    public ResponseEntity<?> getChallengeList(@RequestParam("randomId") String randomId, Pageable pageable) {
        Page<GetChallengeListResponse> challengeListResponse = challengeService.getChallengeList(randomId, pageable);
        return ResponseEntity.ok().body(challengeListResponse);
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<?> getChallenge(@PathVariable("challengeId") Long challengeId) {
        GetChallengeResponse challenge = challengeService.getChallenge(challengeId);
        return ResponseEntity.ok().body(challenge);
    }

}
