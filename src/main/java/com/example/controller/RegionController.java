package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.RegionDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto,
                                            @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(regionService.create(dto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RegionDTO dto,
                                    @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(regionService.update(id, dto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(regionService.delete(id));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("")
    public ResponseEntity<List<RegionDTO>> getAll(@RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.ok(regionService.getAll());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/lang")
    public ResponseEntity<List<RegionDTO>> getByLang(@RequestParam(value = "lang", defaultValue = "uz")
                                                     AppLanguage language) {
        return ResponseEntity.ok(regionService.getByLang(language));
    }


}
