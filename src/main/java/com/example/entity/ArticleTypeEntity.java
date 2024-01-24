package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "article_type")
public class ArticleTypeEntity extends BaseEntity{
    @Column(nullable = false)
    private Integer orderNumber;
    @Column(nullable = false)
    private String name_uz;
    @Column(nullable = false)
    private String name_ru;
    @Column(nullable = false)
    private String name_en;

}
