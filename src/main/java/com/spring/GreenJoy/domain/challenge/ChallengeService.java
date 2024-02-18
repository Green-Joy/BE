package com.spring.GreenJoy.domain.challenge;

import com.spring.GreenJoy.domain.challenge.dto.CreateAndUpdateChallengeRequest;
import com.spring.GreenJoy.domain.challenge.dto.GetChallengeListResponse;
import com.spring.GreenJoy.domain.challenge.dto.GetChallengeResponse;
import com.spring.GreenJoy.domain.challenge.entity.Challenge;
import com.spring.GreenJoy.domain.image.ImageService;
import com.spring.GreenJoy.domain.user.UserRepository;
import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.NanoId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    private final ImageService imageService;

    /**
     * 오늘의 챌린지 생성: scheduler
     */
    // @Scheduled(cron = "*/30 * * * * *") // 30초 마다 변경 (Test Code)
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 변경
    public String createTodayChallenge() {
        List<User> userList  = userRepository.findAll();

        String todayChallenge = getRandomChallenge();
        LocalDate todayDate = LocalDate.now();

        for(User user : userList){
            Challenge challenge = challengeRepository.save(Challenge.builder()
                    .title(todayChallenge)
                    .content(null)
                    .image(null)
                    .user(user)
                    .active(false)
                    .challengeDate(todayDate)
                    .build());
        }

        return todayChallenge;
    }

    /**
     * 오늘의 챌린지 랜덤 생성
     * @return 오늘의 챌린지
     */
    private static String getRandomChallenge() {
        List<String> todayChallengeList = Arrays.asList(
                "텀블러 사용하기", "자전거 또는 대중교통 이용하기", "쓰레기 분리수거하기",
                "지역 농산물 구매하기", "종이 사용 줄이기 (디지털 문서 사용하기)",
                "잔반 남기지 않기", "비건(채식) 식단 해보기", "일회용 비닐 사용 안하기",
                "다회용기를 이용해서 포장하기", "친환경 제품 사용하기", "안 쓰는 콘센트 뽑기",
                "물 사용 절약하기", "샤워 시간 줄이기", "양치컵 쓰기", "중고제품 구매하기",
                "만보 걷기", "걸어서 이동하기", "나갈 때 전등 끄기", "녹색제품 인증 마크 상품 구입하기"
        );

        Random random = new Random();

        return todayChallengeList.get(random.nextInt(todayChallengeList.size()));
    }

    /**
     * 오늘의 챌린지 가져오기
     * @return 오늘의 챌린지
     */
    public String getTodayChallenge() {
        LocalDate todayDate = LocalDate.now();
        List<Challenge> challengeList = challengeRepository.findAllByChallengeDateAndActive(todayDate, false);

        String todayChallenge = null;

        if(!challengeList.isEmpty()) {
            Challenge lastChallenge = challengeList.get(0);
            todayChallenge = lastChallenge.getTitle();

            log.info("오늘의 챌린지는: " + todayChallenge);
        } else {
            return "아직 오늘의 챌린지가 정해지지 않았습니다. 잠시 후 다시 시도해주세요.";
        }

        return todayChallenge;
    }

    /**
     * 오늘의 챌린지 인증 (USER)
     */
    public String certifyChallenge(CreateAndUpdateChallengeRequest createAndUpdateChallengeRequest) throws IOException {
        User user = userRepository.findById(NanoId.of(createAndUpdateChallengeRequest.randomId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        LocalDate todayDate = LocalDate.now();
        // Optional<Challenge> todayChallenge = challengeRepository.findByChallengeDateAndActive(todayDate, false);
        List<Challenge> challengeList = challengeRepository.findAllByChallengeDateAndActiveOrderByCreatedAtDesc(todayDate, false);

        String todayChallenge = null;

        if(!challengeList.isEmpty()) {
            Challenge lastChallenge = challengeList.get(challengeList.size()-1);
            todayChallenge = lastChallenge.getTitle();

            log.info("오늘의 챌린지는: " + todayChallenge);
        } else {
            return "아직 오늘의 챌린지가 정해지지 않았습니다. 잠시 후 다시 시도해주세요.";
        }

        String imageUrl = null;
        imageUrl = imageService.uploadFiles(createAndUpdateChallengeRequest.image());

        Challenge challenge = challengeRepository.save(Challenge.builder()
                //.title(todayChallenge.get().getTitle())
                .title(todayChallenge)
                .content(createAndUpdateChallengeRequest.content())
                .image(imageUrl)
                .user(user)
                .active(true)
                .challengeDate(todayDate)
                .build());

        return challenge.getTitle();
    }

    /**
     * 챌린지 전체 조회(페이징)
     */
    public Page<GetChallengeListResponse> getChallengeList(String randomId, Pageable pageable) {
        Page<Challenge> challengePage = challengeRepository.findByUser_UserIdOrderByCreatedAtDesc(NanoId.of(randomId), pageable);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return challengePage.map(challenge -> GetChallengeListResponse.builder()
                .title(challenge.getTitle())
                // .writer(challenge.getUser().getNickname())
                // .content(challenge.getContent())
                .thumbnail(challenge.getImage())
                .challengeDate(challenge.getCreatedAt().format(dateTimeFormatter))
                .build());
    }

    /**
     * 챌린지 상세 조회
     */
    public GetChallengeResponse getChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 챌린지입니다."));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return GetChallengeResponse.builder()
                .title(challenge.getTitle())
                //.writer(challenge.getUser().getName())
                .content(challenge.getContent())
                .thumbnail(challenge.getImage())
                .challengeDate(challenge.getCreatedAt().format(dateTimeFormatter))
                .build();
    }

}
