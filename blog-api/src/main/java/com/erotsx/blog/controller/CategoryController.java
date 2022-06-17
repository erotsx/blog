package com.erotsx.blog.controller;

import com.erotsx.blog.entity.Category;
import com.erotsx.blog.service.CategoryService;
import com.erotsx.blog.vo.CategoryVo;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.Result;
import com.erotsx.blog.vo.TagVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * @return 所有目录
     */
    @GetMapping("getAllCategories")
    public Result<List<CategoryVo>> getAllCategories() {
        return Result.success(categoryService.getAllCategories());
    }

    /**
     * @param id 删除目录 Id
     * @return String
     */
    @DeleteMapping("delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return Result.success(null, "删除成功");
    }

    /**
     * @param category 目录
     * @return String
     */
    @PostMapping("add")
    public Result<?> add(@RequestBody Category category) {
        categoryService.add(category);
        return Result.success(null, "添加成功");
    }

    /**
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
