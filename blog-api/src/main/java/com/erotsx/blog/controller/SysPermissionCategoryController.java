package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.SysPermissionCategory;
import com.erotsx.blog.service.SysPermissionCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("permissionCategory")
public class SysPermissionCategoryController {

    @Resource
    private SysPermissionCategoryService sysPermissionCategoryService;

    /**
     * 添加后台权限分类
     *
     * @param sysPermissionCategory SysPermissionCategory
     * @return msg
     */
    @PostMapping("create")
    public Result<?> create(@RequestBody SysPermissionCategory sysPermissionCategory) {
        sysPermissionCategoryService.create(sysPermissionCategory);
        return Result.success(null, "创建成功");
    }

    /**
     * 修改后台权限分类
     *
     * @param sysPermissionCategory SysPermissionCategory
     * @return msg
     */
    @PutMapping("update")
    public Result<?> update(@RequestBody SysPermissionCategory sysPermissionCategory) {
        sysPermissionCategoryService.update(sysPermissionCategory);
        return Result.success(null, "修改成功");
    }

    /**
     * 根据Id删除后台权限分类
     *
     * @param id Id
     * @return msg
     */
    @DeleteMapping("delete/{id}")
    public Result<?> delete(@PathVariable("id") Long id) {
        sysPermissionCategoryService.delete(id);
        return Result.success(null, "删除成功");
    }
}
