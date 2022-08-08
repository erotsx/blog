package com.erotsx.blog.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVo {

    private String id;

    private String content;

    private String userId;

    private String nickname;

    private Date createDate;

    private Integer likeCounts;

    private Integer replyCounts;

    private String parentId;

    private String articleId;
}
