package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.vo.Result;
import com.erotsx.blog.vo.SysUserVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SysUserService {
    SysUser findSysUserById(Long authorId);

    SysUser findSysUser(String account, String password);

    SysUserVo getUserInfo(String token);

    SysUser findSysUserByAccount(String account);

    void insert(SysUser sysUser);

    String updateAvatar(MultipartFile file, String token) throws IOException;
}
