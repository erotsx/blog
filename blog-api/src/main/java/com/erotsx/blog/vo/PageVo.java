package com.erotsx.blog.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageVo<T> {

    private Long total;

    private List<T> items;

    public PageVo(List<T> items, Long total) {
        this.items = items;
        this.total = total;
    }

    public PageVo() {
        this.total = 0L;
        this.items = new ArrayList<>();
    }
}
