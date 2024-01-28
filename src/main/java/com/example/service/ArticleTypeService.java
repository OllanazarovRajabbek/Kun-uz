package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        if (dto.getOrderNumber() == null || dto.getName_uz() == null
                || dto.getName_ru() == null || dto.getName_en() == null) {
            throw new AppBadException("The information entered is incorrect. Please try again");
        }
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setName_uz(dto.getName_uz());
        entity.setName_ru(dto.getName_ru());
        entity.setName_en(dto.getName_en());
        entity.setCreatedDate(LocalDateTime.now());

        articleTypeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public Boolean update(Integer id, ArticleTypeDTO dto) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible().equals(false)) {
            throw new AppBadException("The article type you are trying to update could not be found");
        }
        ArticleTypeEntity entity = optional.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setName_uz(dto.getName_uz());
        entity.setName_ru(dto.getName_ru());
        entity.setName_en(dto.getName_en());
        entity.setUpdatedDate(LocalDateTime.now());
        articleTypeRepository.save(entity);

        return true;
    }

    public Boolean delete(Integer id) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible().equals(false)) {
            throw new AppBadException("The article type you want to delete was not found");
        }
        ArticleTypeEntity entity = optional.get();
        entity.setVisible(false);

        articleTypeRepository.save(entity);
        return true;
    }

    public PageImpl<ArticleTypeDTO> getAll(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleTypeEntity> entityPage = articleTypeRepository.findAll(pageable);

        List<ArticleTypeEntity> entityList = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityList) {
            if (entity.getVisible().equals(true)) {
                dtoList.add(toDTO(entity));
            }
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public List<ArticleTypeDTO> getByLang(AppLanguage lang) {
        Iterable<ArticleTypeEntity> entityList = articleTypeRepository.findAll();

        List<ArticleTypeDTO> dtoList = new LinkedList<>();

        for (ArticleTypeEntity entity : entityList) {
            if (entity.getVisible().equals(true)) {
                ArticleTypeDTO dto = new ArticleTypeDTO();
                dto.setId(entity.getId());
                switch (lang) {
                    case UZ -> dto.setName(entity.getName_uz());
                    case RU -> dto.setName(entity.getName_ru());
                    default -> dto.setName(entity.getName_en());
                }
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    public ArticleTypeDTO toDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setName_uz(entity.getName_uz());
        dto.setName_ru(entity.getName_ru());
        dto.setName_en(entity.getName_en());
        dto.setVisible(entity.getVisible());
        if (entity.getUpdatedDate() != null) {
            dto.setCreatedDate(entity.getUpdatedDate());
        }else {
            dto.setCreatedDate(entity.getCreatedDate());
        }
        return dto;
    }
}
