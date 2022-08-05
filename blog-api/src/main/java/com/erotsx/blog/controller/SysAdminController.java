package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.service.SysAdminService;
import com.erotsx.blog.vo.AdminParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("admin")
public class SysAdminController {

    @Resource
    private SysAdminService sysAdminService;

    /**
     * @param adminParams 用户名、密码
     * @return token值
     */
    @PostMapping("login")
    public Result<?> login(@RequestBody AdminParams adminParams) {
        return Result.success(sysAdminService.login(adminParams), "登录成功");
    }

    /**
     * @param token token
     * @return 登出
     */
    @PostMapping("logout")
    public Result<?> logout(@RequestHeader("Authorization") String token) {
        sysAdminService.logout(token);
        return Result.success(null, "登出成功");
    }

    /**
     * 注册用户
     *
     * @param adminParams AdminParams
     * @return token
     */
    @PostMapping("register")
    public Result<?> register(@RequestBody AdminParams adminParams) {
        return Result.success(sysAdminService.register(adminParams), "注册成功");
    }

    /**
     * 修改用户角色
     *
     * @param userId     用户id
     * @param roleIdList 角色id列表
     * @return String
     */
    @PutMapping("updateRole")
    public Result<?> updateRole(@RequestParam Long userId, @RequestParam List<Long> roleIdList) {
        sysAdminService.updateRole(userId, roleIdList);
        return Result.success(null, "修改成功");
    }

    /**
     * 获取用户角色
     *
     * @param id 用户id
     * @return 角色列表
     */
    @GetMapping("listRoles/{id}")
    public Result<List<SysRole>> listRoles(@PathVariable("id") Long id) {
        return Result.success(sysAdminService.listRoles(id));
    }
}
