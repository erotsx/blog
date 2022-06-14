package com.erotsx.blog.controller;

import com.erotsx.blog.service.ArticleService;
import com.erotsx.blog.vo.ArticleVo;
import com.erotsx.blog.vo.PageParams;
import com.erotsx.blog.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * @param pageParams 分页参数
     * @return 根据是否置顶，创建时间返回文章列表
     */
    @GetMapping("getArticles")
    public Result<List<ArticleVo>> getArticles(@RequestBody PageParams pageParams) {
        return Result.success(articleService.getArticles(pageParams));
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
    public Result<ArticleVo> getArticleById(@PathVariable("id") int id) {
        return Result.success(articleService.getArticleById(id));
    }

}
