package com.erotsx.blog.controller;

import com.erotsx.blog.service.AdminService;
import com.erotsx.blog.vo.AdminParams;
import com.erotsx.blog.vo.Result;
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
    public Result login(@RequestBody AdminParams adminParams) {
        return adminService.login(adminParams);
    }

    /**
     * @param token token
     * @return 登出
     */
    @PostMapping("logout")
    public Result logout(@RequestHeader("Authorization") String token) {
        return adminService.logout(token);
    }

    @PostMapping("register")
    public Result register(@RequestBody AdminParams adminParams) {
        return adminService.register(adminParams);
    }

}
