package com.erotsx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.dao.SysUserInfoMapper;
import com.erotsx.blog.entity.SysUserInfo;
import com.erotsx.blog.service.SysUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserInfoServiceImpl implements SysUserInfoService {

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    @Override
    public SysUserInfo findSysUserInfoById(Long authorId) {
        LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserInfo::getUserId, authorId);
        return sysUserInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateById(SysUserInfo sysUserInfo) {
        sysUserInfoMapper.updateById(sysUserInfo);
    }
}
