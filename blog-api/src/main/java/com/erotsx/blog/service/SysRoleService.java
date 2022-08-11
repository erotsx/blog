package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.vo.PageVo;

import java.util.List;

public interface SysRoleService {
    /**
     * 根据角色名称获取角色
     *
     * @param name 角色名称
     * @return 角色
     */
    SysRole getRoleByName(String name);

    /**
     * 添加角色
     *
     * @param sysRole Role
     */
    void create(SysRole sysRole);

    /**
     * 修改角色信息
     *
     * @param sysRole Role
     */
    void update(SysRole sysRole);

    /**
     * 根据Id删除角色
     *
     * @param id 角色Id
     */
    void delete(Long id);

    /**
     * 根据角色Id获取权限
     *
     * @param id 角色Id
     * @return 权限
     */
    List<SysPermission> listPermissions(Long id);

    /**
     * 获取所有角色
     *
     * @return 所有角色
     */
    List<SysRole> listAll();

    /**
     * 修改角色权限
     *
     * @param id               角色Id
     * @param permissionIdList 权限Id列表
     */
    void updatePermissions(Long id, List<Long> permissionIdList);

    /**
     * 根据关键词分页搜索角色
     *
     * @param keyword  角色名称关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 角色列表
     */
    PageVo<SysRole> search(String keyword, int page, int pageSize);
}
