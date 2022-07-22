package com.erotsx.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysPermission {

    private Long id;

    private String name;

    private String url;

    private String description;

    private Date createTime;
}
