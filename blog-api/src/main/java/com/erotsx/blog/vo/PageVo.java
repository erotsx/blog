package com.erotsx.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {

    private long total;

    private List<T> items;

    public PageVo(List<T> items, long total) {
        this.items = items;
        this.total = total;
    }
}
