package com.erotsx.blog.service;

import com.erotsx.blog.entity.Article;
import com.erotsx.blog.entity.Tag;
import com.erotsx.blog.vo.ArticleVo;
import com.erotsx.blog.vo.PageParams;
import com.erotsx.blog.vo.PageVo;

import java.util.List;

public interface ArticleService {
    PageVo<ArticleVo> getArticles(PageParams pageParams);

    List<ArticleVo> getHotArticles(int limit);

    List<ArticleVo> getNewArticles(int limit);

    ArticleVo getArticleById(int id);

    List<ArticleVo> findArticlesByTagId(Integer tagId);

    PageVo<ArticleVo> search(String keyword, String status, Integer tagId, Integer categoryId, PageParams pageParams);
}
