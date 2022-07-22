package com.erotsx.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {

    private Long id;

    private String name;

    private String description;

    private Date createDate;

    private String status;
}
