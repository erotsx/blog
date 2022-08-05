package com.erotsx.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erotsx.blog.entity.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据userId获取角色
     *
     * @param id userId
     * @return SysRole
     */
    SysRole selectByUserId(Long id);

    /**
     * 根据角色id删除角色权限表中的所有关联
     *
     * @param id 角色id
     */
    void deletePermissionRelation(Long id);

    /**
     * 批量插入到角色权限表
     *
     * @param id               角色id
     * @param permissionIdList 权限id列表
     */
    void insertPermissionRelation(Long id, List<Long> permissionIdList);
}
