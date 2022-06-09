package com.erotsx.blog.service;

import com.erotsx.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(int id);

    List<TagVo> getTags(int limit);
}
