package com.example.dto.like;

import com.example.enums.ArticleLikeStatus;
import lombok.Data;

@Data
public class LikeDTO {
    private ArticleLikeStatus status;
}
