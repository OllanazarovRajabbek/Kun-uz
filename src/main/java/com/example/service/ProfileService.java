package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto) {
        if (dto.getName() == null || dto.getSurname() == null || dto.getEmail() == null ||
                dto.getPhone() == null || dto.getPassword() == null || dto.getStatus() == null || dto.getRole() == null) {
            throw new AppBadException("The information entered is incorrect. Please try again");
        }
        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(dto.getEmail());
        if (byEmail.isPresent()) {
            throw new AppBadException("There is such an email user");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());
        entity.setCreatedDate(LocalDateTime.now());

        profileRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(Integer id, ProfileDTO profile) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible().equals(false)) {
            throw new AppBadException("The profile you want to update was not found");
        }
        ProfileEntity profileEntity = optional.get();
        profileEntity.setName(profile.getName());
        profileEntity.setSurname(profile.getSurname());
        profileEntity.setPassword(MD5Util.encode(profile.getPassword()));
        profileEntity.setPhone(profile.getPhone());
        profileEntity.setStatus(profile.getStatus());
        profileEntity.setEmail(profile.getEmail());
        profileEntity.setRole(profile.getRole());
        profileEntity.setUpdatedDate(LocalDateTime.now());

        profileRepository.save(profileEntity);
        return true;
    }

    public Boolean updateDetail(Integer id, ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible().equals(false)) {
            throw new AppBadException("The profile you want to update was not found");
        }
        ProfileEntity profileEntity = optional.get();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setPassword(MD5Util.encode(dto.getPassword()));
        profileEntity.setStatus(dto.getStatus());
        profileEntity.setUpdatedDate(LocalDateTime.now());

        profileRepository.save(profileEntity);
        return true;
    }

    public Boolean delete(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible().equals(false)) {
            throw new AppBadException("The profile you want to delete was not found");
        }
        ProfileEntity entity = optional.get();
        entity.setVisible(false);
        profileRepository.save(entity);
        return true;
    }

    public PageImpl<ProfileDTO> getAll(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProfileEntity> entityPage = profileRepository.findAll(pageable);

        List<ProfileEntity> entityList = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            if (entity.getVisible().equals(true)) {
                dtoList.add(toDTO(entity));
            }
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        if (dto.getUpdatedDate() != null) {
            dto.setCreatedDate(dto.getUpdatedDate());
        }
        dto.setCreatedDate(dto.getCreatedDate());
        return dto;
    }

    public PageImpl<ProfileDTO> filter(Integer page, Integer size, ProfileDTO dto) {


    }
}
