package com.example.controller;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto) {

        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody RegionDTO dto) {
        return ResponseEntity.ok(regionService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<List<RegionDTO>> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<RegionDTO>> getByLang(@RequestParam String lang) {
        return ResponseEntity.ok(regionService.getByLang(lang));
    }


}