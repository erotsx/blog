package com.erotsx.blog.security.filter;

import javax.servlet.http.HttpServletRequest;

import com.erotsx.blog.security.component.DynamicAccessDecisionManager;
import com.erotsx.blog.security.component.DynamicSecurityMetadataSource;
import com.erotsx.blog.security.config.WhiteListConfig;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Resource
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @Resource
    private WhiteListConfig whiteListConfig;

    @Resource
    public void setAccessDecisionManager(DynamicAccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation filterInvocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
        //OPTIONS请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            //执行下一个拦截器
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            return;
        }
        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : whiteListConfig.getWhiteList()) {
            if (pathMatcher.match(path, request.getRequestURI())) {
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                return;
            }
        }
        //此处会首先调用的是 SecurityMetadataSource，来获取当前请求的鉴权规则
        //然后通过Authentication获取当前登录用户所有权限数据
        //最后调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            //执行下一个拦截器
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
}
