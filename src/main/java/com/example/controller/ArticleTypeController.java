package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO article) {
        ArticleTypeDTO result = articleTypeService.create(article);
        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody ArticleTypeDTO article) {
        return ResponseEntity.ok(articleTypeService.update(id, article));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<PageImpl<ArticleTypeDTO>> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "6") Integer size) {
        return ResponseEntity.ok(articleTypeService.getAll(page, size));
    }

    @GetMapping("/lang")
    public ResponseEntity<List<ArticleTypeDTO>> getByLang(@RequestParam String lang) {
        return ResponseEntity.ok(articleTypeService.getByLang(lang));
    }
}
