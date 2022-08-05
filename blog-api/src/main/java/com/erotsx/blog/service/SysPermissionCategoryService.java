package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysPermissionCategory;
import com.erotsx.blog.vo.SysPermissionCategoryVo;

import java.util.List;

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

    /**
     * 获取所有权限目录及其权限列表
     *
     * @return 所有权限目录及其权限列表
     */
    List<SysPermissionCategoryVo> listAll();
}
