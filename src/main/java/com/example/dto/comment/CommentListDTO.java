package com.example.dto.comment;
import com.example.dto.profile.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentListDTO {
    private Integer id;
    private ProfileDTO profile;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

}
