package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.service.SysPermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author erotsx
 */
@RestController
@RequestMapping("permission")
public class SysPermissionController {

    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 添加后台权限
     *
     * @param sysPermission SysPermission
     * @return msg
     */
    @PostMapping("create")
    public Result<?> create(@RequestBody SysPermission sysPermission) {
        sysPermissionService.create(sysPermission);
        return Result.success(null, "创建成功");
    }

    /**
     * 修改后台权限
     *
     * @param sysPermission SysPermission
     * @return msg
     */
    @PutMapping("update")
    public Result<?> update(@RequestBody SysPermission sysPermission) {
        sysPermissionService.update(sysPermission);
        return Result.success(null, "修改成功");
    }

    /**
     * 根据Id删除后台权限
     *
     * @param id id
     * @return msg
     */
    @DeleteMapping("delete/{id}")
    public Result<?> delete(@PathVariable("id") Long id) {
        sysPermissionService.delete(id);
        return Result.success(null, "删除成功");
    }

    /**
     * 查询所有后台权限
     *
     * @return 所有后台权限
     */
    @GetMapping("listAll")
    public Result<List<SysPermission>> listAll() {
        return Result.success(sysPermissionService.listAll());
    }

}
