package com.example.dto;

import com.example.enums.ArticleLikeStatus;
import lombok.Data;

@Data
public class LikeDTO {
    private ArticleLikeStatus status;
}
