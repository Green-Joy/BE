package com.spring.GreenJoy.domain.post;

import com.spring.GreenJoy.domain.post.dto.CreateAndUpdatePostRequest;
import com.spring.GreenJoy.domain.post.dto.DeletePostRequest;
import com.spring.GreenJoy.domain.post.dto.GetPostListResponse;
import com.spring.GreenJoy.domain.post.dto.GetPostResponse;
import com.spring.GreenJoy.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@ModelAttribute CreateAndUpdatePostRequest createAndUpdatePostRequest) throws IOException {
        Long postId = postService.createPost(createAndUpdatePostRequest);
        return ResponseEntity.ok().body("글 작성 성공");
    }

    @GetMapping
    public ResponseEntity<?> getPostList(Pageable pageable) {
        Page<GetPostListResponse> postListResponse = postService.getPostList(pageable);
        return ResponseEntity.ok().body(postListResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable("postId") Long postId) {
        GetPostResponse post = postService.getPost(postId);
        return ResponseEntity.ok().body(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@ModelAttribute CreateAndUpdatePostRequest createAndUpdatePostRequest,
                                        @PathVariable("postId") Long postId) throws IOException {
        Long post = postService.updatePost(createAndUpdatePostRequest, postId);
        return ResponseEntity.ok().body("게시글 수정 완료");
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Long postId, @RequestBody DeletePostRequest deletePostRequest) throws IOException {
        postService.deletePost(postId, deletePostRequest);
        return ResponseEntity.ok().body("게시글 삭제 완료");
    }

}
