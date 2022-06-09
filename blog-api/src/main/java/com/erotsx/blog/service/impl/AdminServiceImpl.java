package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.service.AdminService;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.utils.JWTUtils;
import com.erotsx.blog.vo.AdminParams;
import com.erotsx.blog.vo.Result;
import com.erotsx.blog.vo.ResultCode;
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
    public Result login(AdminParams adminParams) {
        String account = adminParams.getAccount();
        String password = adminParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.failed(ResultCode.VALIDATE_FAILED, "账号或密码为空");
        }
        SysUser sysUser = sysUserService.findSysUser(account, password);
        if (sysUser == null) {
            return Result.failed(ResultCode.VALIDATE_FAILED, "用户名或密码错误");
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return Result.success(map);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(AdminParams adminParams) {
        String account = adminParams.getAccount();
        String password = adminParams.getPassword();
        String nickname = adminParams.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            return Result.failed(ResultCode.VALIDATE_FAILED, "参数存在空值");
        }
        SysUser sysUser = sysUserService.findSysUserByAccount(account);
        if (sysUser != null) {
            return Result.failed(ResultCode.VALIDATE_FAILED, "账户已存在");
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
        return Result.success(map);
    }
}
