package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import com.example.enums.ArticleLikeStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {


    Optional<ArticleLikeEntity>findTop1AllByArticleId(String id);
    @Transactional
    @Modifying
    @Query("update ArticleLikeEntity set status=?2,updatedDate=?3 where id=?1")
    int update(Integer id, ArticleLikeStatus status, LocalDateTime now);

}
