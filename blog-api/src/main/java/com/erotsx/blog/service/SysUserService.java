package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.vo.Result;
import com.erotsx.blog.vo.SysUserVo;

public interface SysUserService {
    SysUser findSysUserById(Long authorId);

    SysUser findSysUser(String account, String password);

    SysUserVo getUserInfo(String token);

    SysUser findSysUserByAccount(String account);

    void insert(SysUser sysUser);
}
