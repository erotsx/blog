package com.erotsx.blog.service.impl;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.common.service.RedisService;
import com.erotsx.blog.dao.ArticleMapper;
import com.erotsx.blog.entity.Article;
import com.erotsx.blog.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
@Slf4j
public class ThreadServiceImpl implements ThreadService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private RedisService redisService;

    @Override
    @Async("taskExecutor")
    public void updateViewCount(Article article) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = ServletUtil.getClientIP(request);
        String key = "articleId_" + article.getId();
        redisService.add(key, ip);
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
