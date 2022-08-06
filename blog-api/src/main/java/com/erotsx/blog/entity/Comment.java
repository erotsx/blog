package com.erotsx.blog.entity;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection="comment")
@CompoundIndex( def = "{'userid': 1, 'nickname': -1}")
public class Comment {

    private String id;

    @Field("content")
    private String content;

//    private Date publishtime;

    @Indexed
    private String userId;

    private String nickname;

    private LocalDateTime createDate;

    private Integer likeCounts;

    private Integer replyCounts;

    private String status;

    private String parentId;

    private String articleId;

}
