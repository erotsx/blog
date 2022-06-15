package com.erotsx.blog.vo;

import lombok.Data;

@Data
public class PageParams {

    private Integer page = 1;

    private Integer pageSize = 10;

    public PageParams(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
