package com.spring.GreenJoy.domain.challenge;

import com.spring.GreenJoy.domain.challenge.entity.Challenge;
import com.spring.GreenJoy.global.common.NanoId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Optional<Challenge> findByChallengeDateAndActiveOrderByCreatedAtDesc(LocalDate todayDate, boolean active);
    List<Challenge> findAllByChallengeDateAndActive(LocalDate todayDate, boolean active);
    List<Challenge> findAllByChallengeDateAndActiveOrderByCreatedAtDesc(LocalDate todayDate, boolean active);
    Page<Challenge> findByUser_UserIdOrderByCreatedAtDesc(NanoId randomId, Pageable pageable);

}
