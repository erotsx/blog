package com.erotsx.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    private int id;

    private Date createDate;

    private Date updateDate;

    private String summary;

    private String title;

    private int viewCounts;

    private int isTop;

    private int authorId;

    private int bodyId;

    private int categoryId;

    private int commentCounts;
}
