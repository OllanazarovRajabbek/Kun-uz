package com.example.service;

import com.example.dto.category.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        if (dto.getOrderNumber() == null || dto.getName_uz() == null
                || dto.getName_ru() == null || dto.getName_en() == null) {
            throw new AppBadException("The information entered is incorrect. Please try again");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setName_uz(dto.getName_uz());
        entity.setName_ru(dto.getName_ru());
        entity.setName_en(dto.getName_en());
        entity.setCreatedDate(LocalDateTime.now());

        categoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(Integer id, CategoryDTO dto) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible().equals(false)) {
            throw new AppBadException("The region you want to update was not found");
        }
        CategoryEntity regionEntity = optional.get();
        regionEntity.setName_uz(dto.getName_uz());
        regionEntity.setName_ru(dto.getName_ru());
        regionEntity.setName_en(dto.getName_en());
        regionEntity.setUpdatedDate(LocalDateTime.now());
        categoryRepository.save(regionEntity);

        return true;
    }

    public Boolean delete(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible().equals(false)) {
            throw new AppBadException("The region you want to delete was not found");
        }
        CategoryEntity entity = optional.get();
        entity.setVisible(false);
        categoryRepository.save(entity);
        return true;
    }

    public List<CategoryDTO> getAll() {
        List<CategoryEntity> getList = categoryRepository.getAllByOrderNumber();

        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity : getList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<CategoryDTO> getByLang(AppLanguage language) {
        Iterable<CategoryEntity> entityList = categoryRepository.findAll();

        List<CategoryDTO> dtoList = new LinkedList<>();

        for (CategoryEntity entity : entityList) {
            if (entity.getVisible().equals(true)) {
                CategoryDTO dto = new CategoryDTO();
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

    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
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
