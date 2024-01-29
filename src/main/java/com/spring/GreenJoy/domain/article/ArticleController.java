package com.spring.GreenJoy.domain.article;

import com.spring.GreenJoy.domain.article.dto.GetArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public List<GetArticleResponse> news(Model model) throws Exception {
        List<GetArticleResponse> articleList = articleService.getFilteredArticles();
        model.addAttribute("article", articleList);

        return articleList;
    }

}
