package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.Category;
import com.erotsx.blog.service.CategoryService;
import com.erotsx.blog.vo.CategoryVo;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.TagVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author erotsx
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 获取所有目录
     *
     * @return 所有目录及数量
     */
    @GetMapping("getAllCategories")
    public Result<PageVo<CategoryVo>> getAllCategories() {
        return Result.success(categoryService.getAllCategories());
    }

    /**
     * 删除目录
     *
     * @param id 目录 id
     * @return msg
     */
    @DeleteMapping("delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return Result.success(null, "删除成功");
    }

    /**
     * 添加目录
     *
     * @param category 目录
     * @return msg
     */
    @PostMapping("add")
    public Result<?> add(@RequestBody Category category) {
        categoryService.add(category);
        return Result.success(null, "添加成功");
    }

    /**
     * 修改目录信息
     *
     * @param category 目录
     * @return msg
     */
    @PutMapping("update")
    public Result<?> update(@RequestBody Category category) {
        categoryService.update(category);
        return Result.success(null, "修改成功");
    }

    /**
     * 查询目录信息
     *
     * @param id 目录id
     * @return CategoryVo
     */
    @GetMapping("info/{id}")
    public Result<CategoryVo> info(@PathVariable Long id) {
        return Result.success(categoryService.getInfo(id));
    }

    /**
     * 搜索目录
     *
     * @param keyword  关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 分页后的 TagVo
     */
    @GetMapping("search")
    public Result<PageVo<CategoryVo>> search(@RequestParam(required = false) String keyword,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(categoryService.search(keyword, page, pageSize));
    }

}
