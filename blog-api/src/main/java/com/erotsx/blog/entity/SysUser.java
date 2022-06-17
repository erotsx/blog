package com.erotsx.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {

    private Long id;

    private String account;

    private String avatar;

    private Date createDate;

    private String email;

    private Date lastLogin;

    private String nickname;

    private String password;

    private String status;
}
