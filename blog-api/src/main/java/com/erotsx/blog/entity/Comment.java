package com.erotsx.blog.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Document(collection = "comment")
@CompoundIndex(def = "{'userid': 1, 'nickname': -1}")
@Data
public class Comment {

    private String id;

    @Field("content")
    private String content;

    @Indexed
    private String userId;

    @Transient
    private String email;

    private String nickname;

    private Date createDate;

    private Integer likeCounts;

    private Integer replyCounts;

    private String parentId;

    private String articleId;

}
