package com.erotsx.blog.service.impl;

import com.erotsx.blog.common.service.RedisService;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private RedisService redisService;

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
}
