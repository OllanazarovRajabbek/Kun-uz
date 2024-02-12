package com.example.controller;

import com.example.dto.category.CategoryDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
                                              HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));

    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CategoryDTO dto,
                                    HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("/adm")
    public ResponseEntity<List<CategoryDTO>> getAll(HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDTO>> getByLang(@RequestParam(value = "lang", defaultValue = "uz")
                                                       AppLanguage language) {
        return ResponseEntity.ok(categoryService.getByLang(language));
    }

}
