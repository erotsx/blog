package com.erotsx.blog.component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.common.service.RedisService;
import com.erotsx.blog.dao.ArticleMapper;
import com.erotsx.blog.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
@EnableScheduling
public class ScheduleTask {

    @Resource
    private RedisService redisService;

    @Resource
    private ArticleMapper articleMapper;


    /**
     * 每天凌晨一点更新文章浏览量
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleView() {
        log.info("更新文章浏览量定时任务开始...");
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<>());
        articles.forEach(article -> {
            String key = "articleId_" + article.getId();
            Long view = redisService.size(key);
            if (view > 0) {
                article.setViewCounts((int) (article.getViewCounts() + view));
                articleMapper.updateById(article);
                redisService.del(key);
            }
        });
        log.info("更新文章浏览量定时任务结束");
    }
}
