package com.example.controller;

import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleCreateIdListDTO;
import com.example.dto.article.ArticleFullInfoDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ArticleCreateDTO dto, HttpServletRequest request) {
        log.info("Create article{}", dto.getTitle());
        Integer moderatorId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, moderatorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ArticleCreateDTO dto, @PathVariable String id,
                                    HttpServletRequest request) {
        log.info("Update article{}", dto.getTitle());
        Integer moderatorId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, moderatorId, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id, HttpServletRequest request) {
        log.info("Delete article by id {}", id);
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_MODERATOR);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PutMapping("change/{id}")
    public ResponseEntity<?> changeStatusById(@PathVariable("id") String id, HttpServletRequest request) {
        log.info("Get article by id {}", id);
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_PUBLISHER);
        return ResponseEntity.ok(articleService.update(id));
    }

    @GetMapping("/typeId/id") //5
    public ResponseEntity<?> getLastFiveArticleByTypes(@RequestParam Integer id, @RequestParam Integer size) {
        log.info("Get last five article by types {}", id);
        return ResponseEntity.ok(articleService.getLastFiveArticleByTypes(id, size));
    }


    @PostMapping("/articles") //7
    public ResponseEntity<List<ArticleFullInfoDTO>> getLastArticlesNotIncludedInList(@RequestBody ArticleCreateIdListDTO dto) {
        log.info("Get Articles witch id not included in given list {}", dto.getArticleId());
        return ResponseEntity.ok(articleService.getLastArticlesNotIncludedInList(dto.getArticleId()));
    }

    @GetMapping("/articleId") //9
    public ResponseEntity<List<ArticleFullInfoDTO>> getLastArticlesByTypesExceptGivenId(@RequestParam String articleId, @RequestParam Integer arTyId) {
        log.info("Get last 4 articles by type and except given article id {}", articleId);
        return ResponseEntity.ok(articleService.getLastArticlesByTypesExceptGivenId(articleId, arTyId));
    }


    @GetMapping("/mostReadArticles") //10
    public ResponseEntity<?> getMostReadArticles() {
        log.info("Get 4 most read articles");
        return ResponseEntity.ok(articleService.getMostReadArticles());
    }

    @GetMapping("/articleListByRegionId") //13
    public ResponseEntity<?> getArticleListByRegionId(@RequestParam Integer id, @RequestParam Integer page,
                                                      @RequestParam Integer size) {
        log.info("Get Article list by Region Key {}", id);
        return ResponseEntity.ok(articleService.getArticleListByRegionId(id, page, size));
    }

    @GetMapping("/lastFiveArticleCategoryKeys") //14
    public ResponseEntity<?> getLastFiveArticleCategoryKey(@RequestParam Integer id) {
        log.info("Get Last Five Article Category Key {}", id);
        return ResponseEntity.ok(articleService.getLastFiveArticleCategoryKey(id));
    }

    @GetMapping("/articlesByCategory") //15
    public ResponseEntity<?> getArticlesByCategoryKey(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                                      @RequestParam("categoryKey") Integer categoryID) {
        log.info("Get Article By Category Key {}", categoryID);
        return ResponseEntity.ok(articleService.getArticlesByCategoryKey(categoryID, page, size));
    }

    @PutMapping("/increaseArticle/{id}") //16
    public ResponseEntity<?> IncreaseArticleViewCount(@PathVariable("id") String id) {
        log.info("Increase Article View Count by Article Id {}", id);
        return ResponseEntity.ok(articleService.IncreaseArticleViewCount(id));
    }

    @PutMapping("/increaseShare/{id}") //17
    public ResponseEntity<?> IncreaseShareViewCount(@PathVariable("id") String id) {
        log.info("Increase Share View Count by Article Id {}", id);
        return ResponseEntity.ok(articleService.IncreaseShareViewCount(id));
    }


}

