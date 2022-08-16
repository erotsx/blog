package com.erotsx.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.common.service.RedisService;
import com.erotsx.blog.dao.SysRolePermissionMapper;
import com.erotsx.blog.dao.SysUserRoleMapper;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysRolePermission;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.entity.SysUserRole;
import com.erotsx.blog.service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private RedisService redisService;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    private final String redis_admin = "blog_admin_";

    private final String redis_permissionList = "blog_permissionList_";

    /**
     * 24小时过期时间
     */
    private final Long redis_expire = 86400L;

    /**
     * 根据账号获取用户
     *
     * @param username 账号
     * @return 用户
     */
    @Override
    public SysUser getAdmin(String username) {
        String key = redis_admin + username;
        return (SysUser) redisService.get(key);
    }

    /**
     * 设置缓存后台用户信息
     *
     * @param sysUser 用户
     */
    @Override
    public void setAdmin(SysUser sysUser) {
        String key = redis_admin + sysUser.getAccount();
        redisService.set(key, sysUser, redis_expire);
    }

    /**
     * 删除后台用户缓存
     *
     * @param username 账号
     */
    @Override
    public void delAdmin(String username) {
        String key = redis_admin + username;
        redisService.del(key);
    }

    /**
     * 根据用户id获取权限
     *
     * @param id 用户id
     * @return 权限
     */
    @Override
    public List<SysPermission> getPermissionList(Long id) {
        String key = redis_permissionList + id;
        return (List<SysPermission>) redisService.get(key);
    }

    /**
     * 设置缓存后台用户权限列表
     *
     * @param id             用户id
     * @param permissionList 权限列表
     */
    @Override
    public void setPermissionList(Long id, List<SysPermission> permissionList) {
        String key = redis_permissionList + id;
        redisService.set(key, permissionList, redis_expire);
    }

    /**
     * 删除后台用户权限列表缓存
     *
     * @param id 用户id
     */
    @Override
    public void delPermissionList(Long id) {
        String key = redis_permissionList + id;
        redisService.del(key);
    }

    /**
     * 当角色相关权限信息改变时删除相关后台用户缓存
     *
     * @param roleId 角色id
     */
    @Override
    public void delPermissionListByRole(Long roleId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getRoleId, roleId);
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(queryWrapper);
        if (CollUtil.isNotEmpty(sysUserRoleList)) {
            List<String> keys = sysUserRoleList.stream()
                    .map(sysUserRole -> redis_permissionList + sysUserRole.getUserId())
                    .collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     *
     * @param roleIds 角色id列表
     */
    @Override
    public void delPermissionListByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysUserRole::getRoleId, roleIds);
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(queryWrapper);
        if (CollUtil.isNotEmpty(sysUserRoleList)) {
            List<String> keys = sysUserRoleList.stream()
                    .map(sysUserRole -> redis_permissionList + sysUserRole.getUserId())
                    .distinct()
                    .collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    /**
     * 当权限信息改变时，删除权限项目后台用户缓存
     *
     * @param permissionId 权限id
     */
    @Override
    public void delPermissionListByPermission(Long permissionId) {
        LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRolePermission::getPermissionId, permissionId);
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectList(queryWrapper);
        List<Long> roleIds = sysRolePermissionList.stream()
                .map(SysRolePermission::getRoleId)
                .distinct()
                .collect(Collectors.toList());
        delPermissionListByRoleIds(roleIds);
    }
}
