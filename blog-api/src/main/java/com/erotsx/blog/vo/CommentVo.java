package com.erotsx.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author erotsx
 */
@Data
public class CommentVo {

    private String id;

    private String content;

    private String userId;

    private String nickname;

    public String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private Integer likeCounts;

    private Integer replyCounts;

    private String parentId;

    private List<ReplyVo> replyVoList;
}
