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

    Tag findTagByTagName(String tagName);

    /**
     * 关联article和tag，向blog_article_tag表中添加数据
     *
     * @param articleId articleId
     * @param tagId     tagId
     */
    void associateTagAndArticle(Long articleId, Long tagId);

    /**
     * 添加新的tag，并返回添加后tag的Id
     *
     * @param tag tag
     * @return id
     */
    Long insert(Tag tag);
}
