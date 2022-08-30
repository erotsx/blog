package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.SysConfig;
import com.erotsx.blog.service.SysConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("config")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 获取系统配置
     *
     * @return 系统配置信息
     */
    @GetMapping("list")
    public Result<SysConfig> list() {
        return Result.success(sysConfigService.list());
    }

    /**
     * 修改系统配置
     *
     * @param sysConfig 配置信息
     * @return msg
     */
    @PutMapping("update")
    public Result<?> update(SysConfig sysConfig) {
        sysConfigService.update(sysConfig);
        return Result.success(null, "修改成功");
    }
}
