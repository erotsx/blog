package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.service.SysRoleService;
import com.erotsx.blog.vo.PageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 添加角色
     *
     * @param sysRole Role
     * @return String
     */
    @PostMapping("create")
    public Result<?> create(@RequestBody SysRole sysRole) {
        sysRoleService.create(sysRole);
        return Result.success(null, "创建成功");
    }

    /**
     * 修改角色信息
     *
     * @param sysRole Role
     * @return String
     */
    @PutMapping("update")
    public Result<?> update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return Result.success(null, "修改成功");
    }

    /**
     * 根据Id删除角色
     *
     * @param id 角色Id
     * @return String
     */
    @DeleteMapping("delete/{id}")
    public Result<?> delete(@PathVariable("id") Long id) {
        sysRoleService.delete(id);
        return Result.success(null, "删除成功");
    }

    /**
     * 根据角色Id获取权限
     *
     * @param id 角色Id
     * @return 权限
     */
    @GetMapping("listPermissions/{id}")
    public Result<List<SysPermission>> listPermissions(@PathVariable("id") Long id) {
        return Result.success(sysRoleService.listPermissions(id));
    }

    /**
     * 获取所有角色
     *
     * @return 所有角色
     */
    @GetMapping("listAll")
    public Result<List<SysRole>> listAll() {
        return Result.success(sysRoleService.listAll());
    }

    /**
     * 修改角色权限
     *
     * @param id               角色Id
     * @param permissionIdList 权限Id列表
     * @return String
     */
    @PostMapping("updatePermissions")
    public Result<?> updatePermissions(@RequestParam Long id, @RequestBody List<Long> permissionIdList) {
        sysRoleService.updatePermissions(id, permissionIdList);
        return Result.success(null, "修改成功");
    }

    /**
     * 根据关键词分页搜索角色
     *
     * @param keyword  角色名称关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 角色列表
     */
    @GetMapping("search")
    public Result<PageVo<SysRole>> search(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(sysRoleService.search(keyword, page, pageSize));
    }
}
