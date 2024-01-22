package com.spring.GreenJoy.domain.likes;

import com.spring.GreenJoy.domain.likes.dto.LikeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<?> createLike(@RequestBody LikeRequest likeRequest) throws Exception {
        likeService.createLike(likeRequest);
        return ResponseEntity.ok().body("좋아요 생성");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLike(@RequestBody LikeRequest likeRequest) throws Exception {
        likeService.deleteLike(likeRequest);
        return ResponseEntity.ok().body("좋아요 삭제");
    }
}
