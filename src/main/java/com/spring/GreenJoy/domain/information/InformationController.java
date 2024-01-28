package com.spring.GreenJoy.domain.information;

import com.spring.GreenJoy.domain.information.dto.CreateAndUpdateInfoRequest;
import com.spring.GreenJoy.domain.information.dto.GetInfoListResponse;
import com.spring.GreenJoy.domain.information.dto.GetInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/infos")
@RequiredArgsConstructor
public class InformationController {

    private final InformationService informationService;

    @PostMapping
    public ResponseEntity<?> createInfo(@ModelAttribute CreateAndUpdateInfoRequest createAndUpdateInfoRequest) throws IOException {
        Long infoId = informationService.createInfo(createAndUpdateInfoRequest);
        return ResponseEntity.ok().body("꿀팁 작성 성공");
    }

    @GetMapping
    public ResponseEntity<?> getInfoList(Pageable pageable) {
        Page<GetInfoListResponse> infoListResponse = informationService.getInfoList(pageable);
        return ResponseEntity.ok().body(infoListResponse);
    }

    @GetMapping("/{infoId}")
    public ResponseEntity<?> getInfp(@PathVariable("infoId") Long infoId) {
        GetInfoResponse info = informationService.getInfo(infoId);
        return ResponseEntity.ok().body(info);
    }

    @PutMapping("/{infoId}")
    public ResponseEntity<?> updateInfo(@ModelAttribute CreateAndUpdateInfoRequest createAndUpdateInfoRequest,
                                        @PathVariable("infoId") Long infoId) throws IOException {
        Long info = informationService.updateInfo(createAndUpdateInfoRequest, infoId);
        return ResponseEntity.ok().body("게시글 수정 완료");
    }

    @DeleteMapping("/{infoId}")
    public ResponseEntity<?> deleteInfo(@PathVariable("infoId") Long infoId) {
        informationService.deleteInfo(infoId);
        return ResponseEntity.ok().body("게시글 삭제 완료");
    }


}
