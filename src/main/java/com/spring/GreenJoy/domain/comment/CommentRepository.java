package com.spring.GreenJoy.domain.comment;

import com.spring.GreenJoy.domain.comment.entity.Comment;
import com.spring.GreenJoy.global.common.NanoId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost_PostId(Long postId, Pageable pageable);
    Optional<Comment> findByUser_UserId(NanoId userId);
}
