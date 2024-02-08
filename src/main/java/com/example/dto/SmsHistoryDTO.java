package com.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsHistoryDTO {
    private Integer id;
    private String message;
    private String phone;
    private LocalDateTime createdData;
}
