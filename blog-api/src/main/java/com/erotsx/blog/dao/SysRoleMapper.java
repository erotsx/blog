package com.erotsx.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erotsx.blog.entity.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据userId获取角色
     *
     * @param id userId
     * @return SysRole
     */
    SysRole selectByUserId(Long id);
}
