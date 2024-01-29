package com.spring.GreenJoy.domain.information;

import com.spring.GreenJoy.domain.information.entity.Information;
import com.spring.GreenJoy.global.common.NanoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InformationRepository extends JpaRepository<Information, Long> {
    Optional<Information> findByUser_UserIdAndInfoId(NanoId userId, Long infoId);
}
