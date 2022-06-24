package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.dao.SysUserMapper;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.exception.Asserts;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.service.ThreadService;
import com.erotsx.blog.utils.ImgBedUtils;
import com.erotsx.blog.utils.JWTUtils;
import com.erotsx.blog.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private ThreadService threadService;

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
        if (!JWTUtils.checkToken(token)) {
            Asserts.fail("token错误");
        }
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

    @Override
    public String updateAvatar(MultipartFile file, String token) throws IOException {
        if (!JWTUtils.checkToken(token)) {
            Asserts.fail("token错误");
        }
        JSONObject data = ImgBedUtils.upload(file);
        SysUser sysUser = sysUserMapper.selectById(JWTUtils.getUserIdFromToken(token));
        String avatar = data.getString("url");
        String delete = data.getString("delete");
        sysUser.setAvatar(avatar);
        if (!StringUtils.isBlank(sysUser.getAvatarDelete())) {
            threadService.deleteAvatar(sysUser.getAvatarDelete());
        }
        sysUser.setAvatarDelete(delete);
        sysUserMapper.updateById(sysUser);
        return avatar;
    }
}
