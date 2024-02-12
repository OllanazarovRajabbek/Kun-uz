package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFullInfoDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private RegionDTO region;
    private CategoryDTO category;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private Integer likeCount;
}
