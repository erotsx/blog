package com.erotsx.blog.security.config;

import com.erotsx.blog.security.component.DynamicAccessDecisionManager;
import com.erotsx.blog.security.component.DynamicSecurityMetadataSource;
import com.erotsx.blog.security.component.RestAuthenticationEntryPoint;
import com.erotsx.blog.security.component.RestfulAccessDeniedHandler;
import com.erotsx.blog.security.filter.DynamicSecurityFilter;
import com.erotsx.blog.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public WhiteListConfig whiteListConfig() {
        return new WhiteListConfig();
    }

    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }
}
