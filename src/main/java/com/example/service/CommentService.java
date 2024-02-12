package com.example.service;

import com.example.dto.comment.CommentCreateDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentListDTO;
import com.example.dto.comment.CommentUpdateDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadException;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;

    public CommentDTO create(CommentCreateDTO dto, Integer profileId, String article_id) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(article_id);
        entity.setProfileId(profileId);
        if (dto.getReplyId() != null) {
            checkReplyId(dto.getReplyId());
            entity.setReplyId(dto.getReplyId());
        }
        commentRepository.save(entity);

        CommentDTO comment = new CommentDTO();
        comment.setId(entity.getId());
        comment.setCreatedDate(entity.getCreatedDate());

        return comment;
    }

    private CommentEntity checkReplyId(Integer replyId) {
        Optional<CommentEntity> optional = commentRepository.findById(replyId);
        if (optional.isEmpty()) throw new AppBadException("comment not  found");
        return optional.get();
    }

    public Boolean update(CommentUpdateDTO dto, Integer profileId, Integer commentId) {
        Optional<CommentEntity> optional = commentRepository.findById(commentId);
        if (optional.isEmpty()) {
            throw new AppBadException("comment not found");
        }
        CommentEntity entity = optional.get();
        if (entity.getProfileId().equals(profileId)) {
            entity.setContent(dto.getContent());
            entity.setProfileId(profileId);
            entity.setArticleId(dto.getArticleId());
            entity.setUpdatedDate(LocalDateTime.now());
            commentRepository.save(entity);
        }
        return true;
    }

    public Boolean delete(Integer profileId, Integer commentId) {
        Optional<CommentEntity> optional = commentRepository.findById(commentId);
        if (optional.isEmpty()) {
            throw new AppBadException("comment not found");
        }
        CommentEntity entity = optional.get();
        if (entity.getProfileId().equals(profileId) && entity.getVisible()) {
            entity.setVisible(false);
            commentRepository.save(entity);
        }
        return true;
    }

    public List<CommentListDTO> getByArticleId(String articleId) {
        List<CommentEntity> allByArticle = commentRepository.getAllByArticleId(articleId);
        List<CommentListDTO> commentList = new LinkedList<>();
        for (CommentEntity comment : allByArticle) {
            commentList.add(toDo(comment));
        }
        return commentList;
    }

    private CommentListDTO toDo(CommentEntity comment) {
        CommentListDTO dto = new CommentListDTO();
        ProfileEntity profileEntity = profileService.get(comment.getProfileId());
        dto.setId(comment.getId());
        dto.setProfile(profileService.toDTO2(profileEntity));
        dto.setCreatedDate(comment.getCreatedDate());
        dto.setUpdateDate(comment.getUpdatedDate());
        return dto;
    }

    public PageImpl<CommentDTO> getAll(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CommentEntity> entityPage = commentRepository.findAll(pageable);

        List<CommentEntity> entityList = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<CommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : entityList) {
            if (entity.getVisible().equals(true)) {
                dtoList.add(toDo2(entity));
            }
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public CommentDTO toDo2(CommentEntity comment) {
        CommentDTO dto = new CommentDTO();
        ProfileEntity profileEntity = profileService.get(comment.getProfileId());
        ArticleEntity articleEntity = articleService.get(comment.getArticleId());
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setReplyId(comment.getReplyId());
        dto.setProfile(profileService.toDTO2(profileEntity));
        dto.setArticle(articleService.toDo(articleEntity));
        dto.setCreatedDate(comment.getCreatedDate());
        dto.setUpdateDate(comment.getUpdatedDate());
        return dto;
    }


}
