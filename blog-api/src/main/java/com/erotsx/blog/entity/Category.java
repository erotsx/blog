package com.erotsx.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Category {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String categoryName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private String description;
}
