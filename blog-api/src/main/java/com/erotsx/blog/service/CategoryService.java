package com.erotsx.blog.service;

import com.erotsx.blog.entity.Category;
import com.erotsx.blog.vo.CategoryVo;
import com.erotsx.blog.vo.PageVo;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);

    PageVo<CategoryVo> getAllCategories();

    Category getCategoryByName(String category);

    void deleteById(Long id);

    void add(Category category);

    PageVo<CategoryVo> search(String keyword, int page, int pageSize);

    void update(Category category);

    CategoryVo getInfo(Long id);

    /**
     * 添加新的category，并返回添加后category的Id
     *
     * @param category category
     * @return id
     */
    Long insert(Category category);
}
