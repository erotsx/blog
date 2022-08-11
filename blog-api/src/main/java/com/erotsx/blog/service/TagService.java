package com.erotsx.blog.service;

import com.erotsx.blog.entity.Tag;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long id);

    /**
     * 获取热门标签
     *
     * @param limit 设置返回数目
     * @return 根据带有此标签的数量降序返回标签
     */
    List<TagVo> getTags(int limit);

    /**
     * 获取所有标签
     *
     * @return 所有标签及数量
     */
    PageVo<TagVo> getAllTags();

    /**
     * 搜索标签
     *
     * @param keyword  关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页后的 TagVo
     */
    PageVo<TagVo> search(String keyword, int page, int pageSize);

    /**
     * 删除标签
     *
     * @param id 标签id
     */
    void deleteById(Long id);

    /**
     * 添加标签
     *
     * @param tag Tag 类
     */
    void add(Tag tag);

    /**
     * 修改标签信息
     *
     * @param tag 标签信息
     */
    void update(Tag tag);

    /**
     * 查询标签信息
     *
     * @param id 根据Id查询标签信息
     * @return tagVo
     */
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

    /**
     * 根据articleId删除blog_article_tag中的关联
     *
     * @param articleId articleId
     */
    void deleteAssociation(Long articleId);
}
