package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(profileService.create(dto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProfileDTO profile,
                                    @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(profileService.update(id, profile));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/detail/{id}")
    public ResponseEntity<?> updateDetail(@RequestHeader(value = "Authorization") String jwt,
                                          @RequestBody ProfileDTO dto) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        return ResponseEntity.ok(profileService.updateDetail(jwtDTO.getId(), dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(profileService.delete(id));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("")
    public ResponseEntity<PageImpl<ProfileDTO>> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                       @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(profileService.getAll(page, size));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                       @RequestBody ProfileDTO dto) {

        return ResponseEntity.ok(profileService.filter(page, size, dto));
    }


}
