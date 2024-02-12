package com.example.service;

import com.example.dto.like.LikeDTO;
import com.example.entity.ArticleLikeEntity;
import com.example.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleLikeService {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public Object like(String article_id, LikeDTO dto, Integer profileId) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findTop1AllByArticleId(article_id);
        ArticleLikeEntity entity = new ArticleLikeEntity();

        if (optional.isEmpty()) {
            entity.setArticleId(article_id);
            entity.setStatus(dto.getStatus());
            entity.setProfileId(profileId);
            articleLikeRepository.save(entity);
            return "Success save";
        }
        ArticleLikeEntity like = optional.get();
        if (like.getStatus().equals(dto.getStatus()) && like.getArticleId().equals(article_id) && like.getProfileId().equals(profileId)) {
            articleLikeRepository.deleteById(like.getId());
            return "Success delete";
        } else if (like.getArticleId().equals(article_id) && !like.getProfileId().equals(profileId)) {
            entity.setArticleId(article_id);
            entity.setStatus(dto.getStatus());
            entity.setProfileId(profileId);
            articleLikeRepository.save(entity);
            return "Success save";
        } else {
            return articleLikeRepository.update(like.getId(), dto.getStatus(), LocalDateTime.now()) != 0 ? "Success update" : "article not found";
        }


    }
}
