package com.example.service;

import com.example.dto.region.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {
        if (dto.getOrderNumber() == null || dto.getName_uz() == null
                || dto.getName_ru() == null || dto.getName_en() == null) {
            throw new AppBadException("The information entered is incorrect. Please try again");
        }

        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setName_uz(dto.getName_uz());
        entity.setName_ru(dto.getName_ru());
        entity.setName_en(dto.getName_en());
        entity.setCreatedDate(LocalDateTime.now());

        regionRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(Integer id, RegionDTO dto) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible().equals(false)) {
            throw new AppBadException("The region you want to update was not found");
        }
        RegionEntity regionEntity = optional.get();
        regionEntity.setName_uz(dto.getName_uz());
        regionEntity.setName_ru(dto.getName_ru());
        regionEntity.setName_en(dto.getName_en());
        regionEntity.setUpdatedDate(LocalDateTime.now());
        regionRepository.save(regionEntity);

        return true;
    }

    public Boolean delete(Integer id) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible().equals(false)) {
            throw new AppBadException("The region you want to delete was not found");
        }
        RegionEntity entity = optional.get();
        entity.setVisible(false);
        regionRepository.save(entity);
        return true;
    }

    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> entityList = regionRepository.findAll();

        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : entityList) {
            if (entity.getVisible().equals(true)) {
                dtoList.add(toDTO(entity));
            }
        }
        return dtoList;
    }

    public List<RegionDTO> getByLang(AppLanguage language) {
        Iterable<RegionEntity> entityList = regionRepository.findAll();

        List<RegionDTO> dtoList = new LinkedList<>();

        for (RegionEntity entity : entityList) {
            if (entity.getVisible().equals(true)) {
                RegionDTO dto = new RegionDTO();
                dto.setId(entity.getId());
                switch (language) {
                    case UZ -> dto.setName_ru(entity.getName_uz());
                    case RU -> dto.setName_en(entity.getName_ru());
                    default -> dto.setName_uz(entity.getName_en());
                }
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    public RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setName_uz(entity.getName_uz());
        dto.setName_ru(entity.getName_ru());
        dto.setName_en(entity.getName_en());
        dto.setVisible(entity.getVisible());
        if (entity.getUpdatedDate() != null) {
            dto.setCreatedDate(entity.getUpdatedDate());
        } else {
            dto.setCreatedDate(entity.getCreatedDate());
        }
        return dto;
    }


}
