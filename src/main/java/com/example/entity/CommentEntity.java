package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.parameters.P;

@Data
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {
    @Column(name = "content")
    private String content;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "reply_id")
    private Integer replyId;
    @ManyToOne
    @JoinColumn(name = "reply_id", insertable = false, updatable = false)
    private CommentEntity reply;

}
