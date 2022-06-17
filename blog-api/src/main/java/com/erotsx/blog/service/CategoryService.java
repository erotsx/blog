package com.erotsx.blog.service;

import com.erotsx.blog.entity.Category;
import com.erotsx.blog.vo.CategoryVo;
import com.erotsx.blog.vo.PageVo;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);

    List<CategoryVo> getAllCategories();

    Category getCategoryByName(String category);

    void deleteById(Long id);

    void add(Category category);

    PageVo<CategoryVo> search(String keyword, int page, int pageSize);
}
