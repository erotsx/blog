package com.erotsx.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Tag {

    @TableId(value="id",type= IdType.ASSIGN_ID)
    private Long id;

    private String avatar;

    private String tagName;
}
