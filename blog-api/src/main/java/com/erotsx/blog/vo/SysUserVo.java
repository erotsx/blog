package com.erotsx.blog.vo;

import com.erotsx.blog.entity.SysRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUserVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String avatar;

    private String nickname;

    private String email;

    private String github;

    private String bilibili;

    private String qq;

    private String introduction;

    private List<SysRole> roleList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLogin;

    private String status;

}
