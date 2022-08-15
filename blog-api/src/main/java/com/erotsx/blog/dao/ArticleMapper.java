package com.erotsx.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erotsx.blog.entity.Article;

import java.util.List;

/**
 * @author erotsx
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Long> findArticleIdsByTagId(Long tagId);
}
