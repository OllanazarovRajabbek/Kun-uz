package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO  {
    private Integer id;
    private String content;
    private Integer profileId;
    @NotNull(message = "articleId is null")
    private String articleId;
    private LocalDateTime createdDate;
    private Integer replyId;
    private LocalDateTime updateDate;
    private Boolean visible;
}
