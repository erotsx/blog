package com.erotsx.blog.service;

import com.erotsx.blog.entity.Article;
import com.erotsx.blog.entity.Tag;
import com.erotsx.blog.vo.ArticleVo;
import com.erotsx.blog.vo.PageParams;
import com.erotsx.blog.vo.PageVo;

import java.util.List;

public interface ArticleService {

    PageVo<ArticleVo> getArticles(int page, int pageSize);

    List<ArticleVo> getHotArticles(int limit);

    List<ArticleVo> getNewArticles(int limit);

    ArticleVo getArticleById(Long id);

    List<ArticleVo> findArticlesByTagId(Long tagId);

    PageVo<ArticleVo> search(String keyword, String status, Long tagId, Long categoryId, PageParams pageParams);

    void deleteArticleById(Long id);

    PageVo<ArticleVo> getArchives();

    void postArticle(ArticleVo articleVo);

    /**
     * 更新文章
     *
     * @param articleVo articleVo
     */
    void updateArticle(ArticleVo articleVo);
}
