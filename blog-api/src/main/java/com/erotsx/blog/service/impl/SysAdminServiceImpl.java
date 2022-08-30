package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.bo.AdminUserDetails;
import com.erotsx.blog.common.exception.Asserts;
import com.erotsx.blog.common.service.RedisService;
import com.erotsx.blog.dao.SysUserMapper;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.security.utils.JWTUtils;
import com.erotsx.blog.service.CacheService;
import com.erotsx.blog.service.SysAdminService;
import com.erotsx.blog.service.SysPermissionService;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.vo.AdminParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Transactional
public class SysAdminServiceImpl implements SysAdminService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysPermissionService sysPermissionService;

    @Resource
    private CacheService cacheService;

    @Resource
    private RedisService redisService;

    /**
     * 根据账号获取用户
     *
     * @param username 账号
     * @return 用户
     */
    @Override
    public SysUser getAdminByUserName(String username) {
        SysUser sysUser = cacheService.getAdmin(username);
        if (sysUser != null) {
            return sysUser;
        }
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, username);
        sysUser = sysUserMapper.selectOne(queryWrapper);
        if (sysUser != null) {
            cacheService.setAdmin(sysUser);
            return sysUser;
        }
        return null;
    }

    /**
     * 用户登录
     *
     * @param adminParams 用户名、密码
     * @return token值
     */
    @Override
    public Map<String, String> login(AdminParams adminParams) {
        UserDetails userDetails = loadUserByUsername(adminParams.getAccount());
        if (!passwordEncoder.matches(adminParams.getPassword(), userDetails.getPassword())) {
            Asserts.fail("密码错误");
        }
        if (!userDetails.isEnabled()) {
            Asserts.fail("账号已被禁用");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = JWTUtils.createToken(userDetails.getUsername());
        redisService.set("TOKEN_" + token, JSON.toJSONString(userDetails), 86400L);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    /**
     * 用户登出
     *
     * @param token token
     */
    @Override
    public void logout(String token) {
        redisService.del("TOKEN_" + token);
    }

    /**
     * 用户注册
     *
     * @param adminParams AdminParams
     * @return token
     */
    @Override
    public Map<String, String> register(AdminParams adminParams) {
        String account = adminParams.getAccount();
        String password = adminParams.getPassword();
        String nickname = adminParams.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            Asserts.fail("参数存在空值");
        }
        SysUser sysUser = sysUserService.findSysUserByAccount(account);
        if (sysUser != null) {
            Asserts.fail("账户已存在");
        }
        sysUser = new SysUser();
        String encodePassword = passwordEncoder.encode(password);
        sysUser.setAccount(account);
//        sysUser.setNickname(nickname);
        sysUser.setPassword(encodePassword);
        sysUser.setStatus("1");
        sysUserService.insert(sysUser);
        return login(adminParams);
    }

    /**
     * 根据账户获取UserDetails
     *
     * @param username 账户
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser sysUser = getAdminByUserName(username);
        if (sysUser != null) {
            List<SysPermission> permissionList = sysPermissionService.getPermissionList(sysUser.getId());
            return new AdminUserDetails(sysUser, permissionList);
        }
        throw new UsernameNotFoundException("账号或密码错误");
    }
}
