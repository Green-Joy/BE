package com.spring.GreenJoy.domain.likes;

import com.spring.GreenJoy.domain.likes.dto.LikeRequest;
import com.spring.GreenJoy.domain.post.PostRepository;
import com.spring.GreenJoy.domain.post.entity.Post;
import com.spring.GreenJoy.domain.user.UserRepository;
import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.NanoId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public void createLike(LikeRequest likeRequest) throws Exception {
        User user = userRepository.findById(NanoId.of(likeRequest.userId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Post post = postRepository.findById(likeRequest.postId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 이미 좋아요 한 경우라면
        if (likeRepository.findByUserAndPost(user, post).isPresent()) {
            throw new Exception();
        }

        post.setLikeCount(post.getLikeCount() + 1);

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);
    }

    public void deleteLike(LikeRequest likeRequest) throws Exception {
        User user = userRepository.findById(NanoId.of(likeRequest.userId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Post post = postRepository.findById(likeRequest.postId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 하지 않은 게시글입니다."));

        post.setLikeCount(post.getLikeCount() - 1);

        likeRepository.delete(like);
    }

}
