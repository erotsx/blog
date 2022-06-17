package com.erotsx.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleVo {

    private Integer id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer isTop;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    private String author;

    private ArticleBodyVo body;

    private List<String> tags;

    private String  categoryName;

    private String status;
}
