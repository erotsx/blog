package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.dao.ArticleMapper;
import com.erotsx.blog.entity.Article;
import com.erotsx.blog.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
@Slf4j
public class ThreadServiceImpl implements ThreadService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    @Async("taskExecutor")
    public void updateViewCount(Article article) {
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        queryWrapper.eq(Article::getViewCounts, article.getViewCounts());
        articleMapper.update(articleUpdate, queryWrapper);
    }

    @Override
    @Async("taskExecutor")
    public void deleteAvatar(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        JSONObject msg = JSON.parseObject(responseEntity.getBody());
        log.info(String.valueOf(msg));
    }
}
