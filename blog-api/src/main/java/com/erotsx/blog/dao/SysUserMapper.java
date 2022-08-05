package com.erotsx.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.entity.SysUser;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser getBlogger(Long roleId);

    /**
     * 根据userId删除用户角色表中关联信息
     *
     * @param userId 用户id
     */
    void deleteRoleRelation(Long userId);

    /**
     * 根据userId修改用户角色
     *
     * @param userId     用户id
     * @param roleIdList 角色id列表
     */
    void insertRoleRelation(Long userId, List<Long> roleIdList);

    /**
     * 根据用户id获取角色
     *
     * @param id 用户id
     * @return 角色列表
     */
    List<SysRole> listRoles(Long id);
}
