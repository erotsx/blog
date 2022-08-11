package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysPermission;

import java.util.List;

public interface SysPermissionService {
    /**
     * 查询所有后台权限
     *
     * @return 所有后台权限
     */
    List<SysPermission> listAll();

    /**
     * 添加后台权限
     *
     * @param sysPermission SysPermission
     */
    void create(SysPermission sysPermission);

    /**
     * 修改后台权限
     *
     * @param sysPermission SysPermission
     */
    void update(SysPermission sysPermission);

    /**
     * 根据Id删除后台权限
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 根据用户Id获取权限列表
     *
     * @param id 用户Id
     * @return 权限列表
     */
    List<SysPermission> getPermissionList(Long id);
}
