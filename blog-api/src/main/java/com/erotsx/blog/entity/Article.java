package com.erotsx.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    private Integer id;

    private Date createDate;

    private Date updateDate;

    private String summary;

    private String title;

    private Integer viewCounts;

    private Integer isTop;

    private Integer authorId;

    private Integer bodyId;

    private Integer categoryId;

    private Integer commentCounts;

    private String status;
}
