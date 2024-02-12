package com.example.dto.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDTO {
    private Integer id;
    private String message;
    private String phone;
    private LocalDateTime createdData;
}
