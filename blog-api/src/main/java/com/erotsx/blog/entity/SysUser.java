package com.erotsx.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysUser {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String account;

    private Date createDate;

    private Date lastLogin;

    private String password;

    private String status;
}
