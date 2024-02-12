package com.example.controller;

import com.example.dto.articleType.ArticleTypeDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/adm")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO article,
                                                 HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        ArticleTypeDTO result = articleTypeService.create(article);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody ArticleTypeDTO article,
                                    HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(articleTypeService.update(id, article));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @GetMapping("/adm")
    public ResponseEntity<PageImpl<ArticleTypeDTO>> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                           HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(articleTypeService.getAll(page, size));
    }

    @GetMapping("/lang")
    public ResponseEntity<List<ArticleTypeDTO>> getByLang(@RequestParam(value = "lang", defaultValue = "uz")
                                                          AppLanguage language) {
        return ResponseEntity.ok(articleTypeService.getByLang(language));
    }


}
