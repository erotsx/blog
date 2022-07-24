package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.service.ArticleService;
import com.erotsx.blog.vo.ArticleVo;
import com.erotsx.blog.vo.PageParams;
import com.erotsx.blog.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
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
     * @param limit 设置返回数目
     * @return 返回热门文章
     */
    @GetMapping("getHotArticles")
    public Result<List<ArticleVo>> getHotArticles(@RequestParam(defaultValue = "10") int limit) {
        return Result.success(articleService.getHotArticles(limit));
    }

    /**
     * @param limit 设置返回数目
     * @return 返回最新文章
     */
    @GetMapping("getNewArticles")
    public Result<List<ArticleVo>> getNewArticles(@RequestParam(defaultValue = "10") int limit) {
        return Result.success(articleService.getNewArticles(limit));
    }

    /**
     * @param id id
     * @return 文章体
     */
    @GetMapping("article/{id}")
    public Result<ArticleVo> getArticleById(@PathVariable("id") Long id) {
        return Result.success(articleService.getArticleById(id));
    }

    /**
     * @param id 删除文章的ID
     * @return return
     */
    @DeleteMapping("delete/{id}")
    public Result<?> deleteArticleById(@PathVariable("id") Long id) {
        articleService.deleteArticleById(id);
        return Result.success(null, "删除成功");
    }

    /**
     * @param keyword    关键词
     * @param status     状态
     * @param tagId      标签id
     * @param categoryId 目录id
     * @param page       page
     * @param pageSize   pageSize
     * @return 文章
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

    @GetMapping("getArchives")
    public Result<PageVo<ArticleVo>> getArchives() {
        return Result.success(articleService.getArchives());
    }


}
