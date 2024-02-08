package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailSendHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer>, PagingAndSortingRepository<EmailHistoryEntity,Integer> {

    List<EmailHistoryEntity> findByEmail(String email);
    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);

    @Query("select count (s) from EmailHistoryEntity s where s.email =?1 and s.createdDate between ?2 and ?3")
    Long countSendEmail(String email, LocalDateTime from, LocalDateTime to);


}
