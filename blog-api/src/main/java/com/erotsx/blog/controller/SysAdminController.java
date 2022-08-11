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
     * 用户登录
     *
     * @param adminParams 用户名、密码
     * @return token值
     */
    @PostMapping("login")
    public Result<?> login(@RequestBody AdminParams adminParams) {
        return Result.success(sysAdminService.login(adminParams), "登录成功");
    }

    /**
     * 用户登出
     *
     * @param token token
     * @return msg
     */
    @PostMapping("logout")
    public Result<?> logout(@RequestHeader("Authorization") String token) {
        sysAdminService.logout(token);
        return Result.success(null, "登出成功");
    }

    /**
     * 用户注册
     *
     * @param adminParams AdminParams
     * @return token
     */
    @PostMapping("register")
    public Result<?> register(@RequestBody AdminParams adminParams) {
        return Result.success(sysAdminService.register(adminParams), "注册成功");
    }
}
