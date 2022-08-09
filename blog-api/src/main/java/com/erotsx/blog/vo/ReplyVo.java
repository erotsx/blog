package com.erotsx.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReplyVo {

    private String id;

    private String parentId;

    private String userId;

    private String nickname;

    private String avatar;

    private String replyUserId;

    private String replyNickname;

    private String content;

    private Integer likeCounts;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}
