package com.erotsx.blog.controller;

import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * @param token token
     * @return 返回用户信息
     */
    @RequestMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader("Authorization") String token) {
        return sysUserService.getUserInfo(token);
    }
}
