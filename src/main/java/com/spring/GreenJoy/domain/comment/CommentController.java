package com.spring.GreenJoy.domain.comment;

import com.spring.GreenJoy.domain.comment.dto.CreateCommentRequest;
import com.spring.GreenJoy.domain.comment.dto.DeleteCommentRequest;
import com.spring.GreenJoy.domain.comment.dto.GetCommentListResponse;
import com.spring.GreenJoy.domain.comment.dto.UpdateCommentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequest createCommentRequest) {
        commentService.createComment(createCommentRequest);
        return ResponseEntity.ok().body("댓글 작성 성공");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getCommentList(@PathVariable("postId") Long postId, Pageable pageable) {
        Page<GetCommentListResponse> commentListResponse = commentService.getCommentList(postId, pageable);
        return ResponseEntity.ok().body(commentListResponse);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId, @RequestBody UpdateCommentRequest updateCommentRequest) {
        commentService.updateComment(commentId, updateCommentRequest);
        return ResponseEntity.ok().body("댓글 수정 성공");
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId, @RequestBody DeleteCommentRequest deleteCommentRequest) {
        commentService.deleteComment(commentId, deleteCommentRequest);
        return ResponseEntity.ok().body("댓글 삭제 성공");
    }
}
