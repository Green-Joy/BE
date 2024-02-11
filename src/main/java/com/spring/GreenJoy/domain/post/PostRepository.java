package com.spring.GreenJoy.domain.post;

import com.spring.GreenJoy.domain.post.entity.Post;
import com.spring.GreenJoy.global.common.NanoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByUser_RandomIdAndPostId(NanoId randomId, Long postId);
}
