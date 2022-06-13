package com.erotsx.blog.service.impl;


import com.erotsx.blog.dao.CategoryMapper;
import com.erotsx.blog.entity.Category;
import com.erotsx.blog.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Category getCategoryById(int id) {
        return categoryMapper.selectById(id);
    }
}
