package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysUser;

import java.util.List;

/**
 * @author erotsx
 */
public interface CacheService {
    /**
     * 获取缓存后台用户信息
     *
     * @param username 账号
     * @return 用户
     */
    SysUser getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     *
     * @param sysUser 用户
     */
    void setAdmin(SysUser sysUser);

    /**
     * 获取缓存后台用户权限列表
     *
     * @param id 用户id
     * @return 权限
     */
    List<SysPermission> getPermissionList(Long id);

    /**
     * 设置缓存后台用户权限列表
     *
     * @param id             用户id
     * @param permissionList 权限列表
     */
    void setPermissionList(Long id, List<SysPermission> permissionList);
}
