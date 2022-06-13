package com.erotsx.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.dao.CategoryMapper;
import com.erotsx.blog.entity.Category;
import com.erotsx.blog.service.CategoryService;
import com.erotsx.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Category getCategoryById(int id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<CategoryVo> getAllCategories() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categories) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category, categoryVo);
            categoryVoList.add(categoryVo);
        }
        return categoryVoList;
    }
}
