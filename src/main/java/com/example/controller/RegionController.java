package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.region.RegionDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.HttpRequestUtil;
import com.example.util.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Region Api List", description = "Api list for Region")
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/adm")
    @Operation(summary = "Api for Create", description = "this Api is used to create a region")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto,
                                            HttpServletRequest request) {
//        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
//        Integer id = (Integer) request.getAttribute("id");
//        ProfileRole role = (ProfileRole) request.getAttribute("role");
//
//        if (role.equals(ProfileRole.ROLE_ADMIN)) {
//            return ResponseEntity.ok(regionService.create(dto));
//        }
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping("/adm/{id}")
    @Operation(summary = "Api for Update", description = "this Api is used for region update")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RegionDTO dto,
                                    HttpServletRequest request) {
        log.info("Region update {}", id);
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    @Operation(summary = "Api for delete", description = "this Api is used for region delete")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    HttpServletRequest request) {
        log.info("Region delete {}", id);
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.delete(id));
    }

    @GetMapping("/adm")
    @Operation(summary = "Api for get all", description = "this Api shows get all regions")
    public ResponseEntity<List<RegionDTO>> getAll(HttpServletRequest request) {

        log.info("Region for get All");
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/lang")
    @Operation(summary = "Api for getBy Language", description = "this api shows to get all locales based on the given language")
    public ResponseEntity<List<RegionDTO>> getByLang(@RequestParam(value = "lang", defaultValue = "uz")
                                                     AppLanguage language) {

        log.warn("The language you entered is incorrect {}", language);
        return ResponseEntity.ok(regionService.getByLang(language));
    }

    @GetMapping("/change")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> change(){
        return ResponseEntity.ok("Done");
    }
    @GetMapping("/change2")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<String> change2() {
        return ResponseEntity.ok("DONE");
    }


}
