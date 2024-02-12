package com.example.controller;

import com.example.dto.comment.CommentCreateDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentListDTO;
import com.example.dto.comment.CommentUpdateDTO;
import com.example.enums.ProfileRole;
import com.example.service.CommentService;
import com.example.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Comment Api for List", description = "This api is for Comment")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/any/{id}")
    @Operation(summary = "Api comment create", description = "Used to create a comment")
    public ResponseEntity<CommentDTO> create(@PathVariable("id") String article_id,
                                             @Valid @RequestBody(required = false) CommentCreateDTO dto,
                                             HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN, ProfileRole.ROLE_PUBLISHER, ProfileRole.ROLE_MODERATOR, ProfileRole.ROLE_USER);
        log.info("There was an error creating a comment {}", article_id);
        return ResponseEntity.ok(commentService.create(dto, profileId, article_id));
    }

    @PutMapping("/any/{id}")
    @Operation(summary = "Api comment update", description = "Used to update a comment")
    public ResponseEntity<?> update(@PathVariable("id") Integer comment_id,
                                    @Valid @RequestBody CommentUpdateDTO dto,
                                    HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN, ProfileRole.ROLE_PUBLISHER, ProfileRole.ROLE_MODERATOR, ProfileRole.ROLE_USER);
        log.info("There was an error updating a comment {}", comment_id);
        return ResponseEntity.ok(commentService.update(dto, profileId, comment_id));
    }

    @DeleteMapping("/any/{id}")
    @Operation(summary = "Api comment delete", description = "Used to delete a comment")
    public ResponseEntity<?> delete(@PathVariable("id") Integer comment_id,
                                    HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN, ProfileRole.ROLE_PUBLISHER, ProfileRole.ROLE_MODERATOR, ProfileRole.ROLE_USER);
        log.info("There was an error deleting a comment {}", comment_id);
        return ResponseEntity.ok(commentService.delete(profileId, comment_id));
    }

    @GetMapping("/any/{id}")
    @Operation(summary = "Api comment getByArticleId", description = "Used to getByArticleId a comment")
    public ResponseEntity<List<CommentListDTO>> getByArticleId(@PathVariable("id") String article_id,
                                                               HttpServletRequest request) {

        log.info("There was an error getByArticleId a comment {}", article_id);
        return ResponseEntity.ok(commentService.getByArticleId(article_id));
    }

    @GetMapping("/adm")
    public ResponseEntity<PageImpl<CommentDTO>> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                       HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(commentService.getAll(page, size));
    }

}

