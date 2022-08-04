package com.erotsx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.dao.SysPermissionMapper;
import com.erotsx.blog.dao.SysRoleMapper;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 查询所有后台权限
     *
     * @return 所有后台权限
     */
    @Override
    public List<SysPermission> listAll() {
        return sysPermissionMapper.selectList(new LambdaQueryWrapper<>());
    }

    /**
     * 添加后台权限
     *
     * @param sysPermission SysPermission
     */
    @Override
    public void create(SysPermission sysPermission) {
        sysPermissionMapper.insert(sysPermission);
    }

    /**
     * 修改后台权限
     *
     * @param sysPermission SysPermission
     */
    @Override
    public void update(SysPermission sysPermission) {
        sysPermissionMapper.updateById(sysPermission);
    }

    /**
     * 根据Id删除后台权限
     *
     * @param id id
     */
    @Override
    public void delete(Long id) {
        sysPermissionMapper.deleteById(id);
    }

    /**
     * 根据用户Id获取权限列表
     *
     * @param id 用户Id
     * @return 权限列表
     */
    @Override
    public List<SysPermission> getPermissionList(Long id) {
        SysRole sysRole = sysRoleMapper.selectByUserId(id);
        return sysPermissionMapper.selectListByRoleId(sysRole.getId());
    }
}
