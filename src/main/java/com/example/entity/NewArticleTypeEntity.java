package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "new_article_type")
public class NewArticleTypeEntity extends BaseEntity {
    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "type_id")
    private Integer typeId;
    @ManyToOne
    @JoinColumn(name = "type_id", insertable = false, updatable = false)
    private ArticleTypeEntity type;
}
