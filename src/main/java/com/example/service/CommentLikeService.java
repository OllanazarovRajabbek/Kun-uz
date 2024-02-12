package com.example.service;

import com.example.dto.like.LikeDTO;
import com.example.entity.CommentLikeEntity;
import com.example.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    public Object like(String article_id, LikeDTO dto, Integer profileId) {
        Optional<CommentLikeEntity> optional = commentLikeRepository.findTop1AllByArticleId(article_id);
        CommentLikeEntity entity = new CommentLikeEntity();

        if (optional.isEmpty()) {
            entity.setArticleId(article_id);
            entity.setStatus(dto.getStatus());
            entity.setProfileId(profileId);
            commentLikeRepository.save(entity);
            return "Success save";
        }
        CommentLikeEntity like = optional.get();
        if (like.getStatus().equals(dto.getStatus()) && like.getArticleId().equals(article_id) && like.getProfileId().equals(profileId)) {
            commentLikeRepository.deleteById(like.getId());
            return "Success delete";
        } else if (like.getArticleId().equals(article_id) && !like.getProfileId().equals(profileId)) {
            entity.setArticleId(article_id);
            entity.setStatus(dto.getStatus());
            entity.setProfileId(profileId);
            commentLikeRepository.save(entity);
            return "Success save";
        } else {
            return commentLikeRepository.update(like.getId(), dto.getStatus(), LocalDateTime.now()) != 0 ? "Success update" : "article not found";
        }


    }
}
