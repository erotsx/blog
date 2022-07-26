package com.erotsx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erotsx.blog.common.exception.Asserts;
import com.erotsx.blog.dao.ArticleBodyMapper;
import com.erotsx.blog.dao.ArticleMapper;
import com.erotsx.blog.entity.Article;
import com.erotsx.blog.entity.ArticleBody;
import com.erotsx.blog.entity.Category;
import com.erotsx.blog.entity.Tag;
import com.erotsx.blog.service.*;
import com.erotsx.blog.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagService tagService;

    @Resource
    private ArticleBodyMapper articleBodyMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private ThreadService threadService;

    @Resource
    private SysUserInfoService sysUserInfoService;

    @Resource
    private SysUserService sysUserService;

    @Override
    public PageVo<ArticleVo> getArticles(int page, int pageSize) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getIsTop, Article::getCreateDate);
        return getArticleVoListByPage(page, pageSize, queryWrapper);
    }

    private PageVo<ArticleVo> getArticleVoListByPage(int page, int pageSize, LambdaQueryWrapper<Article> queryWrapper) {
        Page<Article> pageTemp = new Page<>(page, pageSize);
        Page<Article> articlePage = articleMapper.selectPage(pageTemp, queryWrapper);
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articlePage.getRecords()) {
            ArticleVo articleVo = getArticleVo(article);
            articleVoList.add(articleVo);
        }
        return new PageVo<>(articleVoList, articlePage.getTotal());
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
        queryWrapper.select(Article::getId, Article::getTitle, Article::getCreateDate, Article::getCover);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return getArticleVoList(articles);
    }

    @Override
    public ArticleVo getArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = getArticleVo(article);
        articleVo.setBody(getArticleBodyById(article.getBodyId()));
        threadService.updateViewCount(article);
        return articleVo;
    }

    @Override
    public List<ArticleVo> findArticlesByTagId(Long tagId) {
        List<Long> articleList = articleMapper.findArticleIdsByTagId(tagId);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Article::getId, articleList);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articles) {
            articleVoList.add(getArticleVo(article));
        }
        return articleVoList;
    }

    private ArticleVo getArticleVo(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        List<TagVo> tags = tagService.findTagsByArticleId(article.getId());
        articleVo.setTags(tags);
        articleVo.setAuthor(sysUserInfoService.findSysUserInfoById(article.getAuthorId()).getNickname());
        articleVo.setCategoryName(categoryService.getCategoryById(article.getCategoryId()).getCategoryName());
        return articleVo;
    }

    private ArticleVo getArchiveVo(Article article) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setTitle(article.getTitle());
        articleVo.setCreateDate(article.getCreateDate());
        articleVo.setId(article.getId());
        return articleVo;
    }

    @Override
    public PageVo<ArticleVo> search(String keyword, String status, Long tagId, Long categoryId, PageParams pageParams) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(keyword)) {
            queryWrapper.like(Article::getTitle, keyword);
        }
        if (!StringUtils.isBlank(status)) {
            queryWrapper.eq(Article::getStatus, status);
        }
        queryWrapper.ne(Article::getStatus, "回收站");
        if (tagId != null) {
            List<Long> articleList = articleMapper.findArticleIdsByTagId(tagId);
            if (articleList.isEmpty()) {
                return new PageVo<>();
            }
            queryWrapper.in(Article::getId, articleList);
        }
        if (categoryId != null) {
            queryWrapper.eq(Article::getCategoryId, categoryId);
        }
        if (articleMapper.selectCount(queryWrapper) == 0) {
            return new PageVo<>();
        }
        return getArticleVoListByPage(pageParams.getPage(), pageParams.getPageSize(), queryWrapper);
    }

    @Override
    public void deleteArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        article.setStatus("回收站");
        articleMapper.updateById(article);
    }

    @Override
    public PageVo<ArticleVo> getArchives() {
        Long total = Long.valueOf(articleMapper.selectCount(new LambdaQueryWrapper<>()));
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articles) {
            articleVoList.add(getArchiveVo(article));
        }
        return new PageVo<>(articleVoList, total);
    }

    @Override
    public void postArticle(ArticleVo articleVo) {
        List<TagVo> tagVoList = articleVo.getTags();
        Article article = new Article();
        BeanUtils.copyProperties(articleVo, article);
        article.setCategoryId(getCategoryId(articleVo.getCategoryName()));
        article.setAuthorId(sysUserService.getCurrentUser().getId());
        article.setBodyId(addBody(articleVo.getBody().getContent()));
        articleMapper.insert(article);
        for (TagVo tagVo : tagVoList) {
            Tag tag = tagService.findTagByTagName(tagVo.getTagName());
            if (tag != null) {
                tagService.associateTagAndArticle(article.getId(), tag.getId());
            } else {
                tag = new Tag();
                tag.setTagName(tagVo.getTagName());
                tagService.associateTagAndArticle(article.getId(), tagService.insert(tag));
            }
        }
    }

    /**
     * 更新文章
     *
     * @param articleVo articleVo
     */
    @Override
    public void updateArticle(ArticleVo articleVo) {

    }

    private Long addBody(String content) {
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(content);
        articleBodyMapper.insert(articleBody);
        return articleBody.getId();
    }

    private Long getCategoryId(String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        if (category == null) {
            category = new Category();
            category.setCategoryName(categoryName);
            return categoryService.insert(category);

        } else {
            return category.getId();
        }
    }

    private ArticleBodyVo getArticleBodyById(Long id) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getId, id);
        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody, articleBodyVo);
        return articleBodyVo;
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
