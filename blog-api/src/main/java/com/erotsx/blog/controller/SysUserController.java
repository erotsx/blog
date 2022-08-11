package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.entity.SysUserInfo;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.SysUserVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 获取当前用户信息
     *
     * @return 返回用户信息
     */
    @RequestMapping("getUserInfo")
    public Result<SysUserVo> getUserInfo() {
        return Result.success(sysUserService.getUserInfo());
    }

    /**
     *
     * @param file 头像文件
     * @return 头像 url
     * @throws IOException 异常
     */
    @PutMapping("updateAvatar")
    public Result<String> updateAvatar(@RequestBody MultipartFile file) throws IOException {
        //TODO Apifox
        return Result.success(sysUserService.updateAvatar(file), "修改成功");
    }

    /**
     * @return 返回博主信息
     */
    @GetMapping("getBloggerInfo")
    public Result<SysUserVo> getBloggerInfo() {
        //TODO Apifox
        return Result.success(sysUserService.getBloggerInfo());
    }

    /**
     * 根据关键词分页搜索用户信息
     *
     * @param keyword  昵称关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 用户信息列表
     */
    @GetMapping("search")
    public Result<PageVo<SysUserVo>> search(@RequestParam(required = false) String keyword,
                                            @RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(sysUserService.search(keyword, page, pageSize));
    }

    /**
     * 获取用户角色
     *
     * @param id 用户id
     * @return 角色列表
     */
    @GetMapping("listRoles/{id}")
    public Result<List<SysRole>> listRoles(@PathVariable("id") Long id) {
        return Result.success(sysUserService.listRoles(id));
    }

    /**
     * 修改用户角色
     *
     * @param userId     用户id
     * @param roleIdList 角色id列表
     * @return String
     */
    @PutMapping("updateRole")
    public Result<?> updateRole(@RequestParam Long userId, @RequestBody List<Long> roleIdList) {
        sysUserService.updateRole(userId, roleIdList);
        return Result.success(null, "修改成功");
    }

    /**
     * 修改用户
     *
     * @param sysUser user
     * @return String
     */
    @PutMapping("update")
    public Result<?> update(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser);
        return Result.success(null, "修改成功");
    }

    /**
     * 修改用户信息
     *
     * @param id          用户id
     * @param sysUserInfo 用户信息
     * @return String
     */
    @PutMapping("updateInfo")
    public Result<?> updateInfo(@RequestParam("id") Long id, @RequestBody SysUserInfo sysUserInfo) {
        sysUserService.updateInfo(id, sysUserInfo);
        return Result.success(null, "修改成功");
    }
}
