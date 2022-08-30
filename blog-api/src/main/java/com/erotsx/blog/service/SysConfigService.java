package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysConfig;

public interface SysConfigService {

    /**
     * 修改系统配置
     *
     * @param sysConfig 配置信息
     */
    void update(SysConfig sysConfig);

    /**
     * 获取系统配置
     *
     * @return 系统配置信息
     */
    SysConfig list();
}
