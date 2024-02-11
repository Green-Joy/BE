package com.spring.GreenJoy.domain.post;

import com.spring.GreenJoy.domain.image.ImageService;
import com.spring.GreenJoy.domain.post.dto.CreateAndUpdatePostRequest;
import com.spring.GreenJoy.domain.post.dto.DeletePostRequest;
import com.spring.GreenJoy.domain.post.dto.GetPostListResponse;
import com.spring.GreenJoy.domain.post.dto.GetPostResponse;
import com.spring.GreenJoy.domain.post.entity.Post;
import com.spring.GreenJoy.domain.user.UserRepository;
import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.NanoId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final ImageService imageService;

    // 피드 생성
    public Long createPost(CreateAndUpdatePostRequest createAndUpdatePostRequest) throws IOException {
        User user = userRepository.findByRandomId(NanoId.of(createAndUpdatePostRequest.randomId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        String image1 = null;
        String image2 = null;
        String image3 = null;

        if(createAndUpdatePostRequest.images().size() == 1){
            image1 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(0));
        } else if(createAndUpdatePostRequest.images().size() == 2){
            image1 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(0));
            image2 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(1));
        } else if(createAndUpdatePostRequest.images().size() == 3){
            image1 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(0));
            image2 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(1));
            image3 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(2));
        }

        Post post = postRepository.save(Post.builder()
                .title(createAndUpdatePostRequest.title())
                .content(createAndUpdatePostRequest.content())
                .image1(image1).image2(image2).image3(image3)
                .user(user)
                .build());

        return post.getPostId();
    }

    // 피드 전체 조회(페이징)
    public Page<GetPostListResponse> getPostList(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        return postPage.map(post -> GetPostListResponse.builder()
                .title(post.getTitle())
                .writer(post.getUser().getName())
                .content(post.getContent())
                .thumbnail(post.getImage1())
                .build());
    }

    // 피드 상세 조회
    public GetPostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return GetPostResponse.builder()
                .title(post.getTitle())
                .writer(post.getUser().getName())
                .content(post.getContent())
                .updatedAt(post.getUpdatedAt())
                .image1(post.getImage1())
                .image2(post.getImage2())
                .image3(post.getImage3())
                .build();
    }

    // 피드 수정
    @Transactional
    public Long updatePost(CreateAndUpdatePostRequest createAndUpdatePostRequest, Long postId) throws IOException {
        Post post = postRepository.findByUser_RandomIdAndPostId(NanoId.of(createAndUpdatePostRequest.randomId()), postId)
                .orElseThrow(() -> new IllegalArgumentException("글을 작성한 유저가 아니거나 존재하지 않는 게시글입니다."));

        String image1 = null;
        String image2 = null;
        String image3 = null;

        if(createAndUpdatePostRequest.images().size() == 1){
            image1 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(0));
        } else if(createAndUpdatePostRequest.images().size() == 2){
            image1 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(0));
            image2 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(1));
        } else if(createAndUpdatePostRequest.images().size() == 3){
            image1 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(0));
            image2 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(1));
            image3 = imageService.uploadFiles(createAndUpdatePostRequest.images().get(2));
        }

        post.setTitle(createAndUpdatePostRequest.title());
        post.setContent(createAndUpdatePostRequest.content());
        post.setImage1(image1);
        post.setImage2(image2);
        post.setImage3(image3);

        postRepository.save(post);

        return post.getPostId();
    }

    // 피드 삭제
    public void deletePost(Long postId, DeletePostRequest deletePostRequest) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 글을 작성한 사용자
        User postUser = userRepository.findByUserId(post.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 글을 삭제할려고 요청한 사용자
        User user = userRepository.findByRandomId(NanoId.of(deletePostRequest.randomId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (postUser.getUserId() != user.getUserId()) {
            throw new IllegalArgumentException("글을 작성한 사용자만 삭제할 수 있습니다.");
        }

        List<String> imgUrlList = Arrays.asList(post.getImage1(), post.getImage2(), post.getImage3());
        checkExistenceAndDeleteImage(imgUrlList);

        postRepository.delete(post);
    }

    // 피드의 이미지 삭제
    public void checkExistenceAndDeleteImage(List<String> imgUrls) throws IOException {

        for(String imgUrl: imgUrls) {
            if (imgUrl != null && !imgUrl.isEmpty()) {
                imageService.deleteFiles(imgUrl);
            }
        }

    }
}
