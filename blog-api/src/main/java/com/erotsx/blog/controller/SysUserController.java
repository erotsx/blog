package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.vo.SysUserVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * @return 返回用户信息
     */
    @RequestMapping("getUserInfo")
    public Result<SysUserVo> getUserInfo() {
        return Result.success(sysUserService.getUserInfo());
    }

    /**
     * @param file 头像文件
     * @return 头像 url
     * @throws IOException 异常
     */
    @PutMapping("updateAvatar")
    public Result<String> updateAvatar(@RequestBody MultipartFile file) throws IOException {
        return Result.success(sysUserService.updateAvatar(file), "修改成功");
    }

    /**
     * @return 返回博主信息
     */
    @GetMapping("getBloggerInfo")
    public Result<SysUserVo> getBloggerInfo() {
        return Result.success(sysUserService.getBloggerInfo());
    }
}
