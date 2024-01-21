package com.spring.GreenJoy.domain.likes;

import com.spring.GreenJoy.domain.likes.entity.Like;
import com.spring.GreenJoy.domain.post.entity.Post;
import com.spring.GreenJoy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
