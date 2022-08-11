package com.erotsx.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    private Long id;

    private Date createDate;

    private Date updateDate;

    private String summary;

    private String title;

    private Integer viewCounts;

    private Integer isTop;

    private Long authorId;

    private Long bodyId;

    private Long categoryId;

    private Integer commentCounts;

    private Integer wordCounts;

    private String status;

    private String cover;
}
