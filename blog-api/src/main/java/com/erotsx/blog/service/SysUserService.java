package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.vo.SysUserVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SysUserService {
    SysUser findSysUserById(Long authorId);

    SysUserVo getUserInfo();

    SysUser findSysUserByAccount(String account);

    void insert(SysUser sysUser);

    String updateAvatar(MultipartFile file) throws IOException;

    SysUser getCurrentUser();

    SysUserVo getBloggerInfo();
}
