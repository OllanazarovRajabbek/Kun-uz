package com.example.controller;

import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileFilterDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProfileDTO profile,
                                    HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(profileService.update(id, profile));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDetail(@RequestBody ProfileDTO dto,
                                          HttpServletRequest request) {

        Integer id = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(profileService.updateDetail(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(profileService.delete(id));
    }

    @GetMapping("/adm")
    public ResponseEntity<PageImpl<ProfileDTO>> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                       HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(profileService.getAll(page, size));
    }

    @PostMapping("/adm/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                       @RequestBody ProfileFilterDTO dto, HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        PageImpl<ProfileDTO> result = profileService.filter(page, size, dto);
        return ResponseEntity.ok(result);
    }


}
