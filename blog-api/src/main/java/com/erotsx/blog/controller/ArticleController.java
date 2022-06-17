package com.erotsx.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erotsx.blog.service.ArticleService;
import com.erotsx.blog.vo.ArticleVo;
import com.erotsx.blog.vo.PageParams;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.Result;
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
     * @param pageParams 分页参数
     * @return 根据是否置顶，创建时间返回文章列表
     */
    @GetMapping("getArticles")
    public Result<PageVo<ArticleVo>> getArticles(@RequestBody PageParams pageParams) {
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

    @GetMapping("search")
    public Result<PageVo<ArticleVo>> search(@RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status,
                                            @RequestParam(required = false) Integer tagId,
                                            @RequestParam(required = false) Integer categoryId,
                                            @RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        log.info(keyword+status+tagId+categoryId);
        return Result.success(articleService.search(keyword, status, tagId,categoryId, new PageParams(page, pageSize)));
    }

}
