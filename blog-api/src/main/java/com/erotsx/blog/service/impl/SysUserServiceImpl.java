package com.erotsx.blog.service.impl;

import com.erotsx.blog.dao.SysUserMapper;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findSysUserById(int authorId) {
        return sysUserMapper.selectById(authorId);
    }
}
