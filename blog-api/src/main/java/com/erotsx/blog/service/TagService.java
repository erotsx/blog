package com.erotsx.blog.service;

import com.erotsx.blog.entity.Tag;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long id);

    List<TagVo> getTags(int limit);

    PageVo<TagVo> getAllTags();

    PageVo<TagVo> search(String keyword, int page, int pageSize);

    void deleteById(Long id);

    void add(Tag tag);

    void update(Tag tag);

    TagVo getInfo(Long id);
}
