package com.erotsx.blog.config;

import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.security.component.DynamicSecurityService;
import com.erotsx.blog.service.SysAdminService;
import com.erotsx.blog.service.SysPermissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class BlogSecurityConfig {

    @Resource
    private SysAdminService sysAdminService;

    @Resource
    private SysPermissionService sysPermissionService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> sysAdminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
            List<SysPermission> permissions = sysPermissionService.listAll();
            for (SysPermission permission : permissions) {
                map.put(permission.getUrl(), new org.springframework.security.access.SecurityConfig(permission.getId() + ":" + permission.getName()));
            }
            return map;
        };
    }
}
