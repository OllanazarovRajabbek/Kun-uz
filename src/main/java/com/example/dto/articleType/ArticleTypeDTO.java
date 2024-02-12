package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeDTO {
    private Integer id;
    private Integer orderNumber;
    private String name_uz;
    private String name_ru;
    private String name_en;
    private String name;
    private Boolean visible;
    private LocalDateTime createdDate;

}
