package com.erotsx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erotsx.blog.common.exception.Asserts;
import com.erotsx.blog.dao.TagMapper;
import com.erotsx.blog.entity.Tag;
import com.erotsx.blog.service.TagService;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.TagVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long id) {
        List<Tag> tagList = tagMapper.findTagsByArticleId(id);
        return getTagVoList(tagList);
    }

    /**
     * 获取热门标签
     *
     * @param limit 设置返回数目
     * @return 根据带有此标签的数量降序返回标签
     */
    @Override
    public List<TagVo> getTags(int limit) {
        List<Long> tagIdList = tagMapper.findTagIdList(limit);
        if (CollectionUtils.isEmpty(tagIdList)) {
            return Collections.emptyList();
        }
        List<Tag> tagList = tagMapper.findTagsByTagIdList(tagIdList);
        return getTagVoList(tagList);
    }

    /**
     * 获取所有标签
     *
     * @return 所有标签及数量
     */
    @Override
    public PageVo<TagVo> getAllTags() {
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<>());
        Long total = Long.valueOf(tagMapper.selectCount(new LambdaQueryWrapper<>()));
        return new PageVo<>(getTagVoList(tagList), total);
    }

    /**
     * 搜索标签
     *
     * @param keyword  关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页后的 TagVo
     */
    @Override
    public PageVo<TagVo> search(String keyword, int page, int pageSize) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(keyword)) {
            queryWrapper.like(Tag::getTagName, keyword);
        }
        Page<Tag> tagPages = tagMapper.selectPage(new Page<>(page, pageSize), queryWrapper);
        List<TagVo> tagVoList = getTagVoList(tagPages.getRecords());
        return new PageVo<>(tagVoList, tagPages.getTotal());
    }

    /**
     * 删除标签
     *
     * @param id 标签id
     */
    @Override
    public void deleteById(Long id) {
        tagMapper.deleteArticleTagById(id);
        tagMapper.deleteById(id);
    }

    /**
     * 添加标签
     *
     * @param tag Tag 类
     */
    @Override
    public void add(Tag tag) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getTagName, tag.getTagName());
        if (tagMapper.selectOne(queryWrapper) != null) {
            Asserts.fail("标签已存在");
        } else {
            tagMapper.insert(tag);
        }
    }

    /**
     * 修改标签信息
     *
     * @param tag 标签信息
     */
    @Override
    public void update(Tag tag) {
        tagMapper.updateById(tag);
    }

    /**
     * 查询标签信息
     *
     * @param id 根据Id查询标签信息
     * @return tagVo
     */
    @Override
    public TagVo getInfo(Long id) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId, id);
        Tag tag = tagMapper.selectOne(queryWrapper);
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    @Override
    public Tag findTagByTagName(String tagName) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getTagName, tagName);
        return tagMapper.selectOne(queryWrapper);
    }

    /**
     * 关联article和tag，向blog_article_tag表中添加数据
     *
     * @param articleId articleId
     * @param tagId     tagId
     */
    @Override
    public void associateTagAndArticle(Long articleId, Long tagId) {
        tagMapper.associateTagAndArticle(articleId, tagId);
    }

    /**
     * 添加新的tag，并返回添加后tag的Id
     *
     * @param tag tag
     * @return id
     */
    @Override
    public Long insert(Tag tag) {
        tagMapper.insert(tag);
        return tag.getId();
    }

    /**
     * 根据articleId删除blog_article_tag中的关联
     *
     * @param articleId articleId
     */
    @Override
    public void deleteAssociation(Long articleId) {
        tagMapper.deleteAssociation(articleId);
    }

    private List<TagVo> getTagVoList(List<Tag> tagList) {
        List<TagVo> tags = new ArrayList<>();
        for (Tag tag : tagList) {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tag, tagVo);
            tagVo.setArticleCount(getTagArticleCount(tag.getId()));
            tags.add(tagVo);
        }
        return tags;
    }

    private Integer getTagArticleCount(Long id) {
        return tagMapper.getTagArticleCount(id);
    }
}
