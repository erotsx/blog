package com.erotsx.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class SysUserVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String account;

    private String avatar;

    private String nickname;

}
