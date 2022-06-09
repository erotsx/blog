package com.erotsx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erotsx.blog.dao.ArticleMapper;
import com.erotsx.blog.entity.Article;
import com.erotsx.blog.service.ArticleService;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.service.TagService;
import com.erotsx.blog.vo.ArticleVo;
import com.erotsx.blog.vo.PageParams;
import com.erotsx.blog.vo.TagVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagService tagService;

    @Resource
    private SysUserService sysUserService;

    @Override
    public List<ArticleVo> getArticles(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getIsTop, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articlePage.getRecords()) {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            log.info(String.valueOf(article.getId()));
            List<TagVo> tags = tagService.findTagsByArticleId(article.getId());
            articleVo.setAuthor(sysUserService.findSysUserById(article.getAuthorId()).getNickname());
            articleVo.setTags(tags);
            articleVoList.add(articleVo);
        }
        return articleVoList;

    }

    @Override
    public List<ArticleVo> getHotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return getArticleVoList(articles);

    }

    @Override
    public List<ArticleVo> getNewArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return getArticleVoList(articles);
    }

    private List<ArticleVo> getArticleVoList(List<Article> articles) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articles) {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }
}
