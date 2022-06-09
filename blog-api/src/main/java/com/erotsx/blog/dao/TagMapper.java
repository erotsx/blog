package com.erotsx.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erotsx.blog.entity.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(int id);

    List<Integer> findTagIdList(int limit);

    List<Tag> findTagsByTagIdList(List<Integer> tagIdList);
}
