package com.erotsx.blog.controller;

import com.erotsx.blog.service.CategoryService;
import com.erotsx.blog.vo.CategoryVo;
import com.erotsx.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
