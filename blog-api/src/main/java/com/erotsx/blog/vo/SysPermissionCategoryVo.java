package com.erotsx.blog.vo;

import com.erotsx.blog.entity.SysPermission;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysPermissionCategoryVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private String name;

    private String description;

    private List<SysPermission> children;
}
