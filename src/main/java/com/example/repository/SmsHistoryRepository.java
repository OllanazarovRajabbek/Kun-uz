package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import com.example.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer>, PagingAndSortingRepository<SmsHistoryEntity, Integer> {
    List<SmsHistoryEntity> findByPhone(String email);

    @Query("select count (s) from EmailHistoryEntity s where s.email =?1 and s.createdDate between ?2 and ?3")
    Long countSendEmail(String email, LocalDateTime from, LocalDateTime to);


}
