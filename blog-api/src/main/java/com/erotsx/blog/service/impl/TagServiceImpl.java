package com.erotsx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.dao.TagMapper;
import com.erotsx.blog.entity.Tag;
import com.erotsx.blog.service.TagService;
import com.erotsx.blog.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(int id) {
        List<Tag> tagList = tagMapper.findTagsByArticleId(id);
        return getTagVoList(tagList);
    }

    @Override
    public List<TagVo> getTags(int limit) {
        List<Integer> tagIdList = tagMapper.findTagIdList(limit);
        if (CollectionUtils.isEmpty(tagIdList)) {
            return Collections.emptyList();
        }
        List<Tag> tagList = tagMapper.findTagsByTagIdList(tagIdList);
        return getTagVoList(tagList);
    }

    @Override
    public List<TagVo> getAllTags() {
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<>());
        return getTagVoList(tagList);
    }

    private List<TagVo> getTagVoList(List<Tag> tagList) {
        List<TagVo> tags = new ArrayList<>();
        for (Tag tag : tagList) {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tag, tagVo);
            tags.add(tagVo);
        }
        return tags;
    }
}
