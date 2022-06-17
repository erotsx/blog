package com.erotsx.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erotsx.blog.entity.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Integer> findArticleIdsByTagId(Integer tagId);
}
