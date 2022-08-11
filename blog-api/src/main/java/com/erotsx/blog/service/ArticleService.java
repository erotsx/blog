package com.erotsx.blog.service;

import com.erotsx.blog.entity.Article;
import com.erotsx.blog.entity.Tag;
import com.erotsx.blog.vo.ArticleVo;
import com.erotsx.blog.vo.PageParams;
import com.erotsx.blog.vo.PageVo;

import java.util.List;

public interface ArticleService {

    /**
     * 根据是否置顶，创建时间返回文章列表
     *
     * @param page     page
     * @param pageSize pageSize
     * @return 根据是否置顶，创建时间返回文章列表
     */
    PageVo<ArticleVo> getArticles(int page, int pageSize);

    /**
     * 返回热门文章
     *
     * @param limit 设置返回数目
     * @return 返回热门文章
     */
    List<ArticleVo> getHotArticles(int limit);

    /**
     * 返回最新文章
     *
     * @param limit 设置返回数目
     * @return 返回最新文章
     */
    List<ArticleVo> getNewArticles(int limit);

    /**
     * 根据文章id获取文章
     *
     * @param id id
     * @return 文章
     */
    ArticleVo getArticleById(Long id);

    List<ArticleVo> findArticlesByTagId(Long tagId);

    /**
     * 搜索文章列表
     *
     * @param keyword    关键词
     * @param status     状态
     * @param tagId      标签id
     * @param categoryId 目录id
     * @param pageParams page  pageSize
     * @return 分页文章列表
     */
    PageVo<ArticleVo> search(String keyword, String status, Long tagId, Long categoryId, PageParams pageParams);

    /**
     * 根据文章id删除文章
     *
     * @param id 文章id
     */
    void deleteArticleById(Long id);

    /**
     * 获取文章归档
     *
     * @param page     page
     * @param pageSize pageSize
     * @return 文章归档
     */
    PageVo<ArticleVo> getArchives(int page, int pageSize);

    /**
     * 发表文章
     *
     * @param articleVo articleVo
     */
    void postArticle(ArticleVo articleVo);

    /**
     * 编辑文章
     *
     * @param articleVo articleVo
     */
    void updateArticle(ArticleVo articleVo);
}
