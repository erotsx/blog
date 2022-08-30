package com.erotsx.blog.common.config;

import cn.hutool.extra.servlet.ServletUtil;
import com.erotsx.blog.common.exception.Asserts;
import com.erotsx.blog.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
public class IpLimitInterceptor implements HandlerInterceptor {

    @Resource
    private RedisService redisService;

    private static final String LOCK_IP_KEY = "lock_ip_";

    private static final String IP_REQUEST_TIME_KEY = "ip_request_time_";

    /**
     * 访问次数限制
     */
    private static final long LIMIT_TIME = 3;

    /**
     * Ip封禁时间 单位：秒
     */
    private static final int IP_LOCK_TIME = 3600;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        String ip = ServletUtil.getClientIP(httpServletRequest);
        if (isLock(ip)) {
            log.info("ip访问被禁止={}", ip);
            Asserts.fail("系统繁忙");
            return false;
        }
        if (!addRequestTime(ip, httpServletRequest.getRequestURI())) {
            Asserts.fail("系统繁忙");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }

    private boolean isLock(String ip) {
        return redisService.hasKey(LOCK_IP_KEY + ip);
    }

    private boolean addRequestTime(String ip, String url) {
        String key = ip + ":" + IP_REQUEST_TIME_KEY + url;
        if (redisService.hasKey(key)) {
            long time = redisService.incr(key, 1);
            if (time >= LIMIT_TIME) {
                redisService.set(LOCK_IP_KEY + ip, ip, IP_LOCK_TIME);
                return false;
            }
        } else {
            redisService.set(key, 1L, 10);
        }
        return true;
    }

}
