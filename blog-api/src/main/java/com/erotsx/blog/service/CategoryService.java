package com.erotsx.blog.service;

import com.erotsx.blog.entity.Category;
import com.erotsx.blog.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(int id);

    List<CategoryVo> getAllCategories();

    Category getCategoryByName(String category);
}
