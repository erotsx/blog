package com.erotsx.blog.service.impl;

import com.erotsx.blog.dao.SysPermissionCategoryMapper;
import com.erotsx.blog.entity.SysPermissionCategory;
import com.erotsx.blog.service.SysPermissionCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysPermissionCategoryServiceImpl implements SysPermissionCategoryService {

    @Resource
    private SysPermissionCategoryMapper sysPermissionCategoryMapper;

    /**
     * 添加后台权限分类
     *
     * @param sysPermissionCategory SysPermissionCategory
     */
    @Override
    public void create(SysPermissionCategory sysPermissionCategory) {
        sysPermissionCategoryMapper.insert(sysPermissionCategory);
    }

    /**
     * 修改后台权限分类
     *
     * @param sysPermissionCategory SysPermissionCategory
     */
    @Override
    public void update(SysPermissionCategory sysPermissionCategory) {
        sysPermissionCategoryMapper.updateById(sysPermissionCategory);
    }

    /**
     * 根据Id删除后台权限分类
     *
     * @param id Id
     */
    @Override
    public void delete(Long id) {
        sysPermissionCategoryMapper.deleteById(id);
    }
}
