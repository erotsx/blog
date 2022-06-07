package com.erotsx.blog.service;

import com.erotsx.blog.vo.ArticleVo;
import com.erotsx.blog.vo.PageParams;

import java.util.List;

public interface ArticleService {
    List<ArticleVo> getArticles(PageParams pageParams);
}
