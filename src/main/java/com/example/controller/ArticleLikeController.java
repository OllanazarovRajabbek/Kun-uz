package com.example.controller;

import com.example.dto.like.LikeDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleLikeService;
import com.example.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Article Like for Api", description = "this api is used to like articles")
@RequestMapping("/article_like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/{id}")
    @Operation(summary = "Article Like for api", description = "This method is used to like the article")
    public ResponseEntity<?> like(@PathVariable("id") String article_id,
                                  @RequestBody LikeDTO dto,
                                  HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN, ProfileRole.ROLE_MODERATOR, ProfileRole.ROLE_USER, ProfileRole.ROLE_PUBLISHER);
        log.info("Article like for method {}", article_id);
        return ResponseEntity.ok(articleLikeService.like(article_id, dto, profileId));
    }


}
