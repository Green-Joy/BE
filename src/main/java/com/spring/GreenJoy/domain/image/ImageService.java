package com.spring.GreenJoy.domain.image;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.spring.GreenJoy.global.common.NanoId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class ImageService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    // 생성자를 통해 서비스 객체 초기화
    @Autowired
    public ImageService() {
        Storage storage = StorageOptions.getDefaultInstance().getService();
    }

    public String createImageName() {
        return NanoId.makeId().toString();
    }

    /**
     * 이미지의 URL 문자열에서 파일 이름만 가져오는 함수
     * @param imgUrl
     * @return
     */
    public String extractImgUrl(String imgUrl) {
        String[] parts = imgUrl.split("/");

        return parts[parts.length - 1];
    }

    /**
     * 이미지 업로드 함수
     * @param file
     * @return 업로드된 이미지의 URL 문자열
     * @throws IOException
     */
    public String uploadFiles(MultipartFile file) throws IOException {

        // bucket 관련 변수 선언
        String keyFileName = "greenjoy-c8062-3caf97cb7be4.json";
        InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        // 이미지 파일 이름 생성
        String fileName = createImageName();
        String imgUrl = fileName;

        String newImgUrl = "https://storage.googleapis.com/" + bucketName + "/" + fileName;

        // bucket 저장
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, imgUrl)
                .setContentType(file.getContentType()).build();

        Blob blob = storage.create(blobInfo, file.getInputStream());

        return newImgUrl;
    }

    /**
     * 이미지 삭제 함수
     * @param imgUrl
     * @throws IOException
     */
    public void deleteFiles(String imgUrl) throws IOException {

        // bucket 관련 변수 선언
        String keyFileName = "greenjoy-c8062-3caf97cb7be4.json";
        InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();
        
        // 이미지의 URL 문자열에서 파일 이름 추출
        String parsedImgUrl = extractImgUrl(imgUrl);
        BlobId blobId = BlobId.of(bucketName, parsedImgUrl);

        // 이미지 삭제
        Boolean deleted = storage.delete(blobId);
        
        if(deleted) {
            log.info("이미지가 성공적으로 삭제되었습니다.");
        } else {
            log.info("이미지 삭제에 실패하였습니다.");
        }
    }

}
