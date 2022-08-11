package com.erotsx.blog.entity;

import lombok.Data;

@Data
public class SysUserInfo {

    private Long id;

    private Long userId;

    private String avatar;

    private String nickname;

    private String email;

    private String github;

    private String bilibili;

    private String qq;

    private String avatarDelete;

    private String introduction;
}
