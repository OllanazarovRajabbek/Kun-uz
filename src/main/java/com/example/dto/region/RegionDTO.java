package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    private Integer id;
    @NotNull(message = "OrderNumber required")
    private Integer orderNumber;

    @Size(min = 1,max = 200,message = "nameUz must be between 10 and 200 characters")
    @NotBlank(message = "nameUz is invalid")
    private String name_uz;
    @Size(min = 1,max = 200,message = "nameUz must be between 10 and 200 characters")
    @NotBlank(message = "nameUz is invalid")
    private String name_ru;
    @NotNull(message = "name_en required")
    private String name_en;
    private String name;
    private Boolean visible;
    private LocalDateTime createdDate;
}
