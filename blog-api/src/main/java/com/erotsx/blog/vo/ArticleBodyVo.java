package com.erotsx.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author erotsx
 */
@Data
public class ArticleBodyVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String content;

}
