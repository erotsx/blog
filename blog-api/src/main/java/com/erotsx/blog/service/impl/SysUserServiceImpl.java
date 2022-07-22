package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.bo.AdminUserDetails;
import com.erotsx.blog.dao.SysUserMapper;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.entity.SysUserInfo;
import com.erotsx.blog.service.SysRoleService;
import com.erotsx.blog.service.SysUserInfoService;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.service.ThreadService;
import com.erotsx.blog.utils.ImgBedUtils;
import com.erotsx.blog.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserInfoService sysUserInfoService;

    @Resource
    private ThreadService threadService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public SysUser findSysUserById(Long authorId) {
        return sysUserMapper.selectById(authorId);
    }


    @Override
    public SysUserVo getUserInfo() {

        SysUser sysUser = getCurrentUser();
        SysUserInfo sysUserInfo = sysUserInfoService.findSysUserInfoById(sysUser.getId());
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        BeanUtils.copyProperties(sysUserInfo, sysUserVo);
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
    public String updateAvatar(MultipartFile file) throws IOException {
        JSONObject data = ImgBedUtils.upload(file);
        SysUser sysUser = getCurrentUser();
        SysUserInfo sysUserInfo = sysUserInfoService.findSysUserInfoById(sysUser.getId());
        String avatar = data.getString("url");
        String delete = data.getString("delete");
        if (!StringUtils.isBlank(sysUserInfo.getAvatarDelete())) {
            threadService.deleteAvatar(sysUserInfo.getAvatarDelete());
        }
        sysUserInfo.setAvatar(avatar);
        sysUserInfo.setAvatarDelete(delete);
        sysUserInfoService.updateById(sysUserInfo);
        return avatar;
    }

    @Override
    public SysUser getCurrentUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        AdminUserDetails adminUserDetails = (AdminUserDetails) auth.getPrincipal();
        return adminUserDetails.getSysUser();
    }

    @Override
    public SysUserVo getBloggerInfo() {
        SysRole sysRole = sysRoleService.getRoleByName("blogger");
        SysUser sysUser = sysUserMapper.getBlogger(sysRole.getId());
        SysUserInfo sysUserInfo = sysUserInfoService.findSysUserInfoById(sysUser.getId());
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        BeanUtils.copyProperties(sysUserInfo, sysUserVo);
        return sysUserVo;
    }

}
