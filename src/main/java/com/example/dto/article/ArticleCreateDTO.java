package com.example.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleCreateDTO {
    private String title;
    private String description;
    private String content;
    private String photoId;
    private Integer regionId;
    private Integer categoryId;
    private List<Integer> articleType;
}
