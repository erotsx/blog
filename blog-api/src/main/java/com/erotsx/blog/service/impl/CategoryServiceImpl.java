package com.erotsx.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erotsx.blog.common.exception.Asserts;
import com.erotsx.blog.dao.ArticleMapper;
import com.erotsx.blog.dao.CategoryMapper;
import com.erotsx.blog.entity.Article;
import com.erotsx.blog.entity.Category;
import com.erotsx.blog.service.CategoryService;
import com.erotsx.blog.vo.CategoryVo;
import com.erotsx.blog.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public PageVo<CategoryVo> getAllCategories() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        Long total = Long.valueOf(categoryMapper.selectCount(queryWrapper));
        return new PageVo<>(getCategoryVoList(categories), total);
    }

    private List<CategoryVo> getCategoryVoList(List<Category> categories) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categories) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category, categoryVo);
            categoryVo.setArticleCount(getCategoryArticleCount(category.getId()));
            categoryVoList.add(categoryVo);
        }
        return categoryVoList;
    }

    private Integer getCategoryArticleCount(Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getCategoryId, categoryId);
        return articleMapper.selectCount(queryWrapper);
    }

    @Override
    public Category getCategoryByName(String category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getCategoryName, category);
        return categoryMapper.selectOne(queryWrapper);
    }

    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public void add(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getCategoryName, category.getCategoryName());
        if (categoryMapper.selectOne(queryWrapper) != null) {
            Asserts.fail("目录已存在");
        } else {
            categoryMapper.insert(category);
        }
    }

    @Override
    public PageVo<CategoryVo> search(String keyword, int page, int pageSize) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(keyword)) {
            queryWrapper.like(Category::getCategoryName, keyword);
        }
        Page<Category> categoryPage = categoryMapper.selectPage(new Page<>(page, pageSize), queryWrapper);
        return new PageVo<>(getCategoryVoList(categoryPage.getRecords()), categoryPage.getTotal());
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateById(category);
    }

    @Override
    public CategoryVo getInfo(Long id) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId, id);
        Category category = categoryMapper.selectOne(queryWrapper);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
