package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.dao.SysUserMapper;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.exception.Asserts;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.utils.JWTUtils;
import com.erotsx.blog.vo.Result;
import com.erotsx.blog.vo.ResultCode;
import com.erotsx.blog.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public SysUser findSysUserById(Long authorId) {
        return sysUserMapper.selectById(authorId);
    }

    @Override
    public SysUser findSysUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getId, SysUser::getAccount, SysUser::getAvatar, SysUser::getNickname);
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUserVo getUserInfo(String token) {
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) {
            Asserts.fail("token错误");
        }
        log.info(String.valueOf(map));
        String user = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(user)) {
            Asserts.fail("token错误");
        }
        SysUser sysUser = JSON.parseObject(user, SysUser.class);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        return sysUserVo;
    }

    @Override
    public SysUser findSysUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void insert(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }
}
