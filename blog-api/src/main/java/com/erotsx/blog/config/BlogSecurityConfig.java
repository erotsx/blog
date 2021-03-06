package com.erotsx.blog.config;

import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.security.component.DynamicSecurityService;
import com.erotsx.blog.service.AdminService;
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
    private AdminService adminService;

    @Resource
    private SysPermissionService sysPermissionService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> adminService.loadUserByUsername(username);
    }

//    @Bean
//    public DynamicSecurityService dynamicSecurityService() {
//        return () -> {
//            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
//            //TODO: sysPermissionService listAll()
//            List<SysPermission> permissions = sysPermissionService.listAll();
//            for (SysPermission permission : permissions) {
//                map.put(permission.getUrl(), new org.springframework.security.access.SecurityConfig(permission.getName()));
//            }
//            return map;
//        };
//    }
}
