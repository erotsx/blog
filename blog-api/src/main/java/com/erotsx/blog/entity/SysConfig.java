package com.erotsx.blog.entity;

import lombok.Data;

@Data
public class SysConfig {

    private Long id;

    private Integer photoUploadWay;

    private String imgurlApi;

    private String imgurlUid;

    private String imgurlToken;
}
