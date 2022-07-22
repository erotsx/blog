package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysUserInfo;

public interface SysUserInfoService {
    SysUserInfo findSysUserInfoById(Long authorId);

    void updateById(SysUserInfo sysUserInfo);
}
