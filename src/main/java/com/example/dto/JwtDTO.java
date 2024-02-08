package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtDTO {
    private Integer id;
    private ProfileRole role;

    public JwtDTO(Integer id) {
        this.id = id;
    }
}
