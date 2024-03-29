package com.example.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentUpdateDTO {
    private String content;
    private String articleId;
}
