package com.erotsx.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author erotsx
 */
@Data
public class CategoryVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String categoryName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private Integer articleCount;
}
