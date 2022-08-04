package com.erotsx.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysPermissionCategory {

    private Long id;

    private Date createDate;

    private String name;

    private String description;
}
