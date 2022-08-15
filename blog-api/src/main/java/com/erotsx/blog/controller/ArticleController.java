package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.service.ArticleService;
import com.erotsx.blog.vo.ArticleVo;
import com.erotsx.blog.vo.PageParams;
import com.erotsx.blog.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author erotsx
 */
@Slf4j
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Resource
    private ArticleService articleService;


    /**
     * 根据是否置顶，创建时间返回文章列表
     *
     * @param page     page
     * @param pageSize pageSize
     * @return 根据是否置顶，创建时间返回文章列表
     */
    @GetMapping("getArticles")
    public Result<PageVo<ArticleVo>> getArticles(@RequestParam(required = false, defaultValue = "1") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(articleService.getArticles(page, pageSize));
    }

    /**
     * 返回热门文章
     *
     * @param limit 设置返回数目
     * @return 返回热门文章
     */
    @GetMapping("getHotArticles")
    public Result<List<ArticleVo>> getHotArticles(@RequestParam(defaultValue = "10") int limit) {
        return Result.success(articleService.getHotArticles(limit));
    }

    /**
     * 返回最新文章
     *
     * @param limit 设置返回数目
     * @return 返回最新文章
     */
    @GetMapping("getNewArticles")
    public Result<List<ArticleVo>> getNewArticles(@RequestParam(defaultValue = "10") int limit) {
        return Result.success(articleService.getNewArticles(limit));
    }

    /**
     * 根据文章id获取文章
     *
     * @param id id
     * @return 文章
     */
    @GetMapping("article/{id}")
    public Result<ArticleVo> getArticleById(@PathVariable("id") Long id) {
        return Result.success(articleService.getArticleById(id));
    }

    /**
     * 根据文章id删除文章
     *
     * @param id 文章id
     * @return msg
     */
    @DeleteMapping("delete/{id}")
    public Result<?> deleteArticleById(@PathVariable("id") Long id) {
        articleService.deleteArticleById(id);
        return Result.success(null, "删除成功");
    }

    /**
     * 搜索文章列表
     *
     * @param keyword    关键词
     * @param status     状态
     * @param tagId      标签id
     * @param categoryId 目录id
     * @param page       page
     * @param pageSize   pageSize
     * @return 分页文章列表
     */
    @GetMapping("search")
    public Result<PageVo<ArticleVo>> search(@RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status,
                                            @RequestParam(required = false) Long tagId,
                                            @RequestParam(required = false) Long categoryId,
                                            @RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(articleService.search(keyword, status, tagId, categoryId, new PageParams(page, pageSize)));
    }

    /**
     * 获取文章归档
     *
     * @param page     page
     * @param pageSize pageSize
     * @return 文章归档
     */
    @GetMapping("getArchives")
    public Result<PageVo<ArticleVo>> getArchives(@RequestParam(required = false, defaultValue = "1") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(articleService.getArchives(page, pageSize));
    }

    /**
     * 发表文章
     *
     * @param articleVo articleVo
     * @return msg
     */
    @PostMapping("postArticle")
    public Result<?> postArticle(@RequestBody ArticleVo articleVo) {
        articleService.postArticle(articleVo);
        return Result.success(null, "success");
    }

    /**
     * 编辑文章
     *
     * @param articleVo articleVo
     * @return msg
     */
    @PutMapping("updateArticle")
    public Result<?> updateArticle(@RequestBody ArticleVo articleVo) {
        articleService.updateArticle(articleVo);
        return Result.success(null, "success");
    }
}
