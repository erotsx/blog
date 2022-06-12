package com.erotsx.blog.entity;

import lombok.Data;

@Data
public class ArticleBody {

    private Integer id;

    private String content;

    private String contentHtml;

    private Integer articleId;
}
