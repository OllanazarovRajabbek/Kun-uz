package com.example.dto.article;

import com.example.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfoDTO {
    private String id;
    private String title;
    private String description;
    private AttachDTO photo;
    private LocalDateTime publishedDate;
}
