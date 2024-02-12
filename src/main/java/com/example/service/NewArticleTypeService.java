package com.example.service;

import com.example.entity.NewArticleTypeEntity;
import com.example.repository.NewArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewArticleTypeService {
    @Autowired
    private NewArticleTypeRepository newArticleTypeRepository;

    public void create(String article, List<Integer> typeList) {
        for (Integer typeId : typeList) {
            create(article, typeId);
        }
    }

    public void create(String articleId, Integer typeId) {
        NewArticleTypeEntity entity = new NewArticleTypeEntity();
        entity.setArticleId(articleId);
        entity.setTypeId(typeId);

        newArticleTypeRepository.save(entity);
    }

    public void merge(String articleId, List<Integer> newList) {
        //oldin [7,8,1,2,3]
        //keyin [5]
        List<NewArticleTypeEntity> articleList = newArticleTypeRepository.findArticleId(articleId);
        for (NewArticleTypeEntity newArticleTypeEntity : articleList) {
            int count = 0;
            for (Integer natId : newList) {
                if (newArticleTypeEntity.getTypeId().equals(natId)) {

                    count++;
                }
            }
            if (count == 0) {
                newArticleTypeRepository.deleteEn(newArticleTypeEntity.getArticleId(), newArticleTypeEntity.getTypeId());
            } else {
                newArticleTypeRepository.updateDate(LocalDateTime.now(), newArticleTypeEntity.getArticleId(), newArticleTypeEntity.getTypeId());
                newList.remove(newArticleTypeEntity.getTypeId());
            }

        }
        for (Integer integer : newList) {
            NewArticleTypeEntity entity = new NewArticleTypeEntity();
            entity.setArticleId(articleId);
            entity.setTypeId(integer);
            newArticleTypeRepository.save(entity);
        }
    }


}
