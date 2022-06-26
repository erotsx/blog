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
     * @param token token
     * @return 返回用户信息
     */
    @RequestMapping("getUserInfo")
    public Result<SysUserVo> getUserInfo(@RequestHeader("Authorization") String token) {
        return Result.success(sysUserService.getUserInfo(token));
    }

    /**
     * @param file 头像文件
     * @return 头像 url
     * @throws IOException 异常
     */
    @PutMapping("updateAvatar")
    public Result<String> updateAvatar(@RequestBody MultipartFile file, @RequestHeader("Authorization") String token) throws IOException {
        return Result.success(sysUserService.updateAvatar(file,token), "修改成功");
    }
}
