package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.entity.SysUserInfo;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.SysUserVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SysUserService {
    SysUser findSysUserById(Long authorId);

    /**
     * 获取当前用户信息
     *
     * @return 返回用户信息
     */
    SysUserVo getUserInfo();

    SysUser findSysUserByAccount(String account);

    void insert(SysUser sysUser);

    String updateAvatar(MultipartFile file) throws IOException;

    SysUser getCurrentUser();

    SysUserVo getBloggerInfo();

    /**
     * 根据关键词分页搜索用户信息
     *
     * @param keyword  昵称关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 用户信息列表
     */
    PageVo<SysUserVo> search(String keyword, int page, int pageSize);

    /**
     * 获取用户角色
     *
     * @param id 用户id
     * @return 角色列表
     */
    List<SysRole> listRoles(Long id);

    /**
     * 修改用户角色
     *
     * @param userId     用户id
     * @param roleIdList 角色id列表
     */
    void updateRole(Long userId, List<Long> roleIdList);

    /**
     * 修改用户
     *
     * @param sysUser user
     */
    void update(SysUser sysUser);

    /**
     * 修改用户信息
     *
     * @param id          用户id
     * @param sysUserInfo 用户信息
     */
    void updateInfo(Long id, SysUserInfo sysUserInfo);
}
