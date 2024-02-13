package com.spring.GreenJoy.domain.information;

import com.spring.GreenJoy.domain.image.ImageService;
import com.spring.GreenJoy.domain.information.dto.CreateAndUpdateInfoRequest;
import com.spring.GreenJoy.domain.information.dto.DeleteInfoRequest;
import com.spring.GreenJoy.domain.information.dto.GetInfoListResponse;
import com.spring.GreenJoy.domain.information.dto.GetInfoResponse;
import com.spring.GreenJoy.domain.information.entity.Information;
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
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InformationService {

    private final UserRepository userRepository;
    private final InformationRepository informationRepository;
    private final ImageService imageService;

    // 꿀팁 생성
    public Long createInfo(CreateAndUpdateInfoRequest createAndUpdateInfoRequest) throws IOException {
        User user = userRepository.findByRandomId(NanoId.of(createAndUpdateInfoRequest.randomId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        String image1 = null;
        String image2 = null;
        String image3 = null;

        if(createAndUpdateInfoRequest.images().size() == 1){
            image1 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(0));
        } else if(createAndUpdateInfoRequest.images().size() == 2){
            image1 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(0));
            image2 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(1));
        } else if(createAndUpdateInfoRequest.images().size() == 3){
            image1 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(0));
            image2 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(1));
            image3 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(2));
        }

        Information info = informationRepository.save(Information.builder()
                .title(createAndUpdateInfoRequest.title())
                .content(createAndUpdateInfoRequest.content())
                .image1(image1).image2(image2).image3(image3)
                .user(user)
                .build());

        return info.getInfoId();
    }

    // 꿀팁 전체 조회
    public Page<GetInfoListResponse> getInfoList(Pageable pageable) {
        Page<Information> infoPage = informationRepository.findAll(pageable);

        return infoPage.map(info -> GetInfoListResponse.builder()
                .title(info.getTitle())
                .writer(info.getUser().getName())
                .content(info.getContent())
                .thumbnail(info.getImage1())
                .build());
    }

    // 꿀팁 상세 조회
    public GetInfoResponse getInfo(Long infoId) {
        Information info = informationRepository.findById(infoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return GetInfoResponse.builder()
                .title(info.getTitle())
                .writer(info.getUser().getName())
                .content(info.getContent())
                .updatedAt(info.getUpdatedAt())
                .image1(info.getImage1())
                .image2(info.getImage2())
                .image3(info.getImage3())
                .build();
    }

    // 꿀팁 수정
    @Transactional
    public Long updateInfo(CreateAndUpdateInfoRequest createAndUpdateInfoRequest, Long infoId) throws IOException {
        Information info = informationRepository.findByUser_RandomIdAndInfoId(NanoId.of(createAndUpdateInfoRequest.randomId()), infoId)
                .orElseThrow(() -> new IllegalArgumentException("글을 작성한 유저가 아니거나 존재하지 않는 게시글입니다."));

        String image1 = null;
        String image2 = null;
        String image3 = null;

        if(createAndUpdateInfoRequest.images().size() == 1){
            image1 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(0));
        } else if(createAndUpdateInfoRequest.images().size() == 2){
            image1 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(0));
            image2 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(1));
        } else if(createAndUpdateInfoRequest.images().size() == 3){
            image1 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(0));
            image2 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(1));
            image3 = imageService.uploadFiles(createAndUpdateInfoRequest.images().get(2));
        }

        info.setTitle(createAndUpdateInfoRequest.title());
        info.setContent(createAndUpdateInfoRequest.content());
        info.setImage1(image1);
        info.setImage2(image2);
        info.setImage3(image3);

        informationRepository.save(info);

        return info.getInfoId();
    }

    // 꿀팁 삭제
    public void deleteInfo(Long infoId, DeleteInfoRequest deleteInfoRequest) throws IOException {
        Information info = informationRepository.findById(infoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 꿀팁을 작성한 사용자
        User infoUser = userRepository.findByUserId(info.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 꿀팁을 삭제하려고 요청한 사용자
        User user = userRepository.findByRandomId(NanoId.of(deleteInfoRequest.randomId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (infoUser.getUserId() != user.getUserId()) {
            throw new IllegalArgumentException("글을 작성한 사용자만 삭제할 수 있습니다.");
        }

        List<String> imgUrlList = Arrays.asList(info.getImage1(), info.getImage2(), info.getImage3());
        checkExistenceAndDeleteImage(imgUrlList);

        informationRepository.delete(info);
    }

    public void checkExistenceAndDeleteImage(List<String> imgUrls) throws IOException {

        for(String imgUrl: imgUrls) {
            if (imgUrl != null && !imgUrl.isEmpty()) {
                imageService.deleteFiles(imgUrl);
            }
        }

    }

}
