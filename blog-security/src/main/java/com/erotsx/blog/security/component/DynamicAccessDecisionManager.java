package com.erotsx.blog.security.component;

import cn.hutool.core.collection.CollUtil;
import com.erotsx.blog.common.exception.Asserts;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DynamicAccessDecisionManager implements AccessDecisionManager {


    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 如果授权规则为空则代表此URL无需授权就能访问
        if (CollUtil.isEmpty(configAttributes)) {
            return;
        }
        // 判断授权规则和当前用户所属权限是否匹配
        for (ConfigAttribute configAttribute : configAttributes) {
            String needAuthority = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
                    return;
                }
            }
        }
        // 走到这里就代表没有权限，必须要抛出异常，否则错误处理器捕捉不到
        throw new AccessDeniedException("没有相关权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
