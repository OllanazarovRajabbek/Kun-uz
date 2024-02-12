package com.example.repository;

import com.example.entity.CommentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Integer>, PagingAndSortingRepository<CommentEntity, Integer> {

    @Query("from CommentEntity where articleId=?1 and visible=true")
    List<CommentEntity> getAllByArticleId(String id);

}
