package com.spring.GreenJoy.domain.article;

import com.spring.GreenJoy.domain.article.dto.GetArticleResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    /**
     * Web Crawling Code
     * @return Article 목록
     * @throws IOException
     */
    @PostConstruct
    public List<GetArticleResponse> getArticles() throws IOException {
        List<GetArticleResponse> articleList = new ArrayList<>();
        String articleUrl = "https://www.hkbs.co.kr/news/articleList.html?sc_section_code=S1N1&view_type=sm";
        Document document = Jsoup.connect(articleUrl).get();

        Elements contents = document.select("section ul.type2 li");

        for (Element content : contents) {
            GetArticleResponse article = GetArticleResponse.builder()
                    .image(content.select("a img").attr("abs:src")) // 이미지
                    .title(content.select("h4 a").text()) // 제목
                    .url(content.select("a").attr("abs:href")) // 링크
                    .build();
            articleList.add(article);
        }

        return articleList;
    }

    /**
     * Crawling 된 Article 목록 중 이미지가 없는 Article 제거
     * @return Filtered Article List
     * @throws IOException
     */
    public List<GetArticleResponse> getFilteredArticles() throws IOException {
        List<GetArticleResponse> newsList = getArticles();

        return newsList.stream()
                .filter(GetArticleResponse::validateImage)
                .collect(Collectors.toList());
    }

}
