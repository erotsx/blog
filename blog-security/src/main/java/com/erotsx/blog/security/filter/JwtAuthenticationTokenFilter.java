package com.erotsx.blog.security.filter;

import com.erotsx.blog.security.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取token字符串并解析
        String token = request.getHeader("Authorization");
        if (!StringUtils.isBlank(token)) {
            String account = JWTUtils.getAccountFromToken(token);
            if (!StringUtils.isBlank(account) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 查询出用户对象
                UserDetails userDetails = userDetailsService.loadUserByUsername(account);
                if (JWTUtils.checkToken(token)) {
                    // 手动组装一个认证对象
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // 存储有关身份认证的其他信息的，例如 IP 地址、证书信息等等
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 将认证对象放到上下文中
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
