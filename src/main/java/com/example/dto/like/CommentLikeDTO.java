package com.example.dto.like;

import com.example.entity.ArticleEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ArticleLikeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDTO {
    private String id;
    private ProfileEntity profile;
    private ArticleEntity article;
    private LocalDateTime createdDate;
    private ArticleLikeStatus status;
}
