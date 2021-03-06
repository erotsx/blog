package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.service.AdminService;
import com.erotsx.blog.vo.AdminParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * @param adminParams 用户名、密码
     * @return token值
     */
    @PostMapping("login")
    public Result<?> login(@RequestBody AdminParams adminParams) {
        return Result.success(adminService.login(adminParams), "登录成功");
    }

    /**
     * @param token token
     * @return 登出
     */
    @PostMapping("logout")
    public Result<?> logout(@RequestHeader("Authorization") String token) {
        adminService.logout(token);
        return Result.success(null, "登出成功");
    }

    @PostMapping("register")
    public Result<?> register(@RequestBody AdminParams adminParams) {
        return Result.success(adminService.register(adminParams), "注册成功");
    }

}
