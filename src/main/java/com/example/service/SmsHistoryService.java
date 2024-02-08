package com.example.service;

import com.example.dto.SmsHistoryDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.SmsHistoryEntity;
import com.example.exp.AppBadException;
import com.example.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository historyRepository;

    public void createSms(RegistrationDTO dto, String text) {
        SmsHistoryEntity emailEntity = new SmsHistoryEntity();
        emailEntity.setPhone(dto.getEmail());
        emailEntity.setMessage(text);
        historyRepository.save(emailEntity);
    }

    public List<SmsHistoryDTO> getByPhone(String phone) {
        List<SmsHistoryEntity> entity = historyRepository.findByPhone(phone);
        if (entity.isEmpty()) {
            throw new AppBadException("This Email exists");
        }
        List<SmsHistoryDTO> dtoList = new LinkedList<>();
        for (SmsHistoryEntity smsHistory : entity) {
            dtoList.add(get(smsHistory));
        }
        return dtoList;
    }

    public SmsHistoryDTO get(SmsHistoryEntity entity) {
        SmsHistoryDTO dto = new SmsHistoryDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setMessage(entity.getMessage());
        dto.setCreatedData(entity.getCreatedDate());
        return dto;
    }

    public Object getEmailByGivenDate(LocalDate date) {
        return null;
    }

    public PageImpl<SmsHistoryDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<SmsHistoryEntity> entityPage = historyRepository.findAll(pageable);

        List<SmsHistoryEntity> entityList = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<SmsHistoryDTO> dtoList = new LinkedList<>();
        for (SmsHistoryEntity entity : entityList) {
            dtoList.add(get(entity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }
}
