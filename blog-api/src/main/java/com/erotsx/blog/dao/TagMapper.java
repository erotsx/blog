package com.erotsx.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erotsx.blog.entity.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long id);

    List<Long> findTagIdList(int limit);

    List<Tag> findTagsByTagIdList(List<Long> tagIdList);

    void deleteArticleTagById(Long id);

    Integer getTagArticleCount(Long id);

    void associateTagAndArticle(Long articleId, Long tagId);
}
