package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.exception.Asserts;
import com.erotsx.blog.service.AdminService;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.utils.JWTUtils;
import com.erotsx.blog.vo.AdminParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Map<String, String> login(AdminParams adminParams) {
        String account = adminParams.getAccount();
        String password = adminParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            Asserts.fail("账号或密码为空");
        }
        SysUser sysUser = sysUserService.findSysUser(account, password);
        if (sysUser == null) {
            Asserts.fail("用户名或密码错误");
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
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
        sysUser.setAccount(account);
        sysUser.setNickname(nickname);
        sysUser.setPassword(password);
        sysUserService.insert(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
