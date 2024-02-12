package com.example.dto.profile;

import com.example.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private ProfileRole role;
    private LocalDate fromDate;
    private LocalDate toDate;
}
