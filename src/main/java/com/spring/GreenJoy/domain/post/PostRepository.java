package com.spring.GreenJoy.domain.post;

import com.spring.GreenJoy.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
