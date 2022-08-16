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
     * 删除后台用户缓存
     *
     * @param username 账号
     */
    void delAdmin(String username);


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

    /**
     * 删除后台用户权限列表缓存
     *
     * @param id 用户id
     */
    void delPermissionList(Long id);

    /**
     * 当角色相关权限信息改变时删除相关后台用户缓存
     *
     * @param roleId 角色id
     */
    void delPermissionListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     *
     * @param roleIds 角色id列表
     */
    void delPermissionListByRoleIds(List<Long> roleIds);

    /**
     * 当权限信息改变时，删除权限项目后台用户缓存
     *
     * @param permissionId 权限id
     */
    void delPermissionListByPermission(Long permissionId);
}
