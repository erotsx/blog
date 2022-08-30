package com.erotsx.blog.service.impl;

import com.erotsx.blog.dao.SysConfigMapper;
import com.erotsx.blog.entity.SysConfig;
import com.erotsx.blog.service.SysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;

    /**
     * 修改系统配置
     *
     * @param sysConfig 配置信息
     */
    @Override
    public void update(SysConfig sysConfig) {
        sysConfigMapper.updateById(sysConfig);
    }

    /**
     * 获取系统配置
     *
     * @return 系统配置信息
     */
    @Override
    public SysConfig list() {
        return sysConfigMapper.selectById(1);
    }
}

