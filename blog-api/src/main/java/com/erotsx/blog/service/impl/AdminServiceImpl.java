package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.bo.AdminUserDetails;
import com.erotsx.blog.common.exception.Asserts;
import com.erotsx.blog.dao.SysUserMapper;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.security.utils.JWTUtils;
import com.erotsx.blog.service.AdminService;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.vo.AdminParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

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
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(userDetails), 1, TimeUnit.DAYS);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
    }

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
        String token = JWTUtils.createToken(sysUser.getAccount());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, username);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (sysUser == null) {
            Asserts.fail("用户不存在");
        }
        return new AdminUserDetails(sysUser);
    }
}
