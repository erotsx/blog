package com.erotsx.blog.service;

import com.erotsx.blog.vo.AdminParams;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Map;

/**
 * @author erotsx
 */
public interface SysAdminService {

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

    UserDetails loadUserByUsername(String username);

}
