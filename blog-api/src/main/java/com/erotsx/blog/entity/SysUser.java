package com.erotsx.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {

    private Long id;

    private String account;

    private Date createDate;

    private Date lastLogin;

    private String password;

    private String status;
}
