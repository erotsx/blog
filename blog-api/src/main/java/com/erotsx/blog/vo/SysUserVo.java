package com.erotsx.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class SysUserVo {

    private String avatar;

    private String nickname;

    private String email;

    private String github;

    private String bilibili;

    private String qq;

    private String introduction;

}
