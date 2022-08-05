package com.erotsx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erotsx.blog.dao.SysPermissionMapper;
import com.erotsx.blog.dao.SysRoleMapper;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.service.SysRoleService;
import com.erotsx.blog.vo.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 根据角色名称获取角色
     *
     * @param name 角色名称
     * @return 角色
     */
    @Override
    public SysRole getRoleByName(String name) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getName, name);
        return sysRoleMapper.selectOne(queryWrapper);
    }

    /**
     * 添加角色
     *
     * @param sysRole Role
     */
    @Override
    public void create(SysRole sysRole) {
        sysRoleMapper.insert(sysRole);
    }

    /**
     * 修改角色信息
     *
     * @param sysRole Role
     */
    @Override
    public void update(SysRole sysRole) {
        sysRoleMapper.updateById(sysRole);
    }

    /**
     * 根据Id删除角色
     *
     * @param id 角色Id
     */
    @Override
    public void delete(Long id) {
        sysRoleMapper.deleteById(id);
    }

    /**
     * 根据角色Id获取权限
     *
     * @param id 角色Id
     * @return 权限
     */
    @Override
    public List<SysPermission> listPermissions(Long id) {
        return sysPermissionMapper.selectListByRoleId(id);
    }

    /**
     * 获取所有角色
     *
     * @return 所有角色
     */
    @Override
    public List<SysRole> listAll() {
        return sysRoleMapper.selectList(new LambdaQueryWrapper<>());
    }

    /**
     * 修改角色权限
     *
     * @param id               角色Id
     * @param permissionIdList 权限Id列表
     */
    @Override
    public void updatePermissions(Long id, List<Long> permissionIdList) {
        sysRoleMapper.deletePermissionRelation(id);
        sysRoleMapper.insertPermissionRelation(id, permissionIdList);
    }

    /**
     * 根据关键词分页搜索角色
     *
     * @param keyword  角色名称关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 角色列表
     */
    @Override
    public PageVo<SysRole> search(String keyword, int page, int pageSize) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(keyword)) {
            queryWrapper.like(SysRole::getName, keyword);
        }
        Page<SysRole> rolePage = sysRoleMapper.selectPage(new Page<>(page, pageSize), queryWrapper);

        return new PageVo<>(rolePage.getRecords(), rolePage.getTotal());
    }
}
