package com.erotsx.blog.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleVo {

    private int id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int isTop;

    private Date createDate;

    private String author;

//    private ArticleBodyVo body;
//
//    private List<TagVo> tags;
//
//    private List<CategoryVo> category;
}
