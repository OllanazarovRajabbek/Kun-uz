package com.example.service;

import com.example.dto.history.EmailHistoryDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.exp.AppBadException;
import com.example.repository.EmailSendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailSendHistoryRepository historyRepository;

    public void createEmail(RegistrationDTO dto, String text) {
        EmailHistoryEntity emailEntity = new EmailHistoryEntity();
        emailEntity.setEmail(dto.getEmail());
        emailEntity.setMessage(text);
        historyRepository.save(emailEntity);
    }

    public List<EmailHistoryDTO> getByEmail(String email) {
        List<EmailHistoryEntity> entity = historyRepository.findByEmail(email);
        if (entity.isEmpty()) {
            throw new AppBadException("This Email exists");
        }
        List<EmailHistoryDTO>dtoList=new LinkedList<>();
        for (EmailHistoryEntity emailHistory : entity) {
            dtoList.add(get(emailHistory));
        }
        return dtoList;
    }

    public EmailHistoryDTO get(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        dto.setCreatedData(entity.getCreatedDate());
        return dto;
    }

    public Object getEmailByGivenDate(LocalDate date) {
        return null;
    }

    public PageImpl<EmailHistoryDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<EmailHistoryEntity> entityPage = historyRepository.findAll(pageable);

        List<EmailHistoryEntity> entityList = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        for (EmailHistoryEntity entity : entityList) {
            dtoList.add(get(entity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }
}
