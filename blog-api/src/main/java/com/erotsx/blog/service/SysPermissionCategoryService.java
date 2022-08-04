package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysPermissionCategory;

public interface SysPermissionCategoryService {
    /**
     * 添加后台权限分类
     *
     * @param sysPermissionCategory SysPermissionCategory
     */
    void create(SysPermissionCategory sysPermissionCategory);

    /**
     * 修改后台权限分类
     *
     * @param sysPermissionCategory SysPermissionCategory
     */
    void update(SysPermissionCategory sysPermissionCategory);

    /**
     * 根据Id删除后台权限分类
     *
     * @param id Id
     */
    void delete(Long id);
}
