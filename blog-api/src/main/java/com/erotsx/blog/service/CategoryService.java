package com.erotsx.blog.service;

import com.erotsx.blog.entity.Category;
import com.erotsx.blog.vo.CategoryVo;
import com.erotsx.blog.vo.PageVo;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);

    /**
     * 获取所有目录
     *
     * @return 所有目录及数量
     */
    PageVo<CategoryVo> getAllCategories();

    Category getCategoryByName(String category);

    /**
     * 删除目录
     *
     * @param id 目录 id
     */
    void deleteById(Long id);

    /**
     * 添加目录
     *
     * @param category 目录
     */
    void add(Category category);

    /**
     * 搜索目录
     *
     * @param keyword  关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页后的 TagVo
     */
    PageVo<CategoryVo> search(String keyword, int page, int pageSize);

    /**
     * 修改目录信息
     *
     * @param category 目录
     */
    void update(Category category);

    /**
     * 查询目录信息
     *
     * @param id 目录id
     * @return CategoryVo
     */
    CategoryVo getInfo(Long id);

    /**
     * 添加新的category，并返回添加后category的Id
     *
     * @param category category
     * @return id
     */
    Long insert(Category category);
}
