package com.erotsx.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erotsx.blog.entity.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * 根据角色Id获取权限列表
     *
     * @param id 角色Id
     * @return 权限列表
     */
    List<SysPermission> selectListByRoleId(Long id);
}
