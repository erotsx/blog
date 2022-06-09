package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.vo.Result;

public interface SysUserService {
    SysUser findSysUserById(int authorId);

    SysUser findSysUser(String account, String password);

    Result getUserInfo(String token);

    SysUser findSysUserByAccount(String account);

    void insert(SysUser sysUser);
}
