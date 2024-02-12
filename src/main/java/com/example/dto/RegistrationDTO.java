package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;

}
