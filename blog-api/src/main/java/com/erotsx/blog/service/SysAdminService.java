package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.vo.AdminParams;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;
import java.util.Map;

/**
 * @author erotsx
 */
public interface SysAdminService {

    /**
     * 根据账号获取用户
     *
     * @param username 账号
     * @return 用户
     */
    SysUser getAdminByUserName(String username);



    /**
     * 用户登录
     *
     * @param adminParams 用户名、密码
     * @return token值
     */
    Map<String, String> login(AdminParams adminParams);

    /**
     * 用户登出
     *
     * @param token token
     */
    void logout(String token);

    /**
     * 用户注册
     *
     * @param adminParams AdminParams
     * @return token
     */
    Map<String, String> register(AdminParams adminParams);

    /**
     * 根据账户获取UserDetails
     *
     * @param username 账户
     * @return UserDetails
     */
    UserDetails loadUserByUsername(String username);

}
