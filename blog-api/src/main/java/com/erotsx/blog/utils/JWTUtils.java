package com.erotsx.blog.utils;

import com.erotsx.blog.exception.Asserts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTUtils {

    private static String secret;

    private static Long expiration;


    /**
     * 根据 userId 生成 token
     *
     * @param userId userId
     * @return token
     */
    public static String createToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("created", new Date());
        return createToken(claims);
    }

    /**
     * 根据 claims 生成 token
     *
     * @param claims claims
     * @return token
     */
    private static String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(createExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 生成 token 过期时间
     *
     * @return Date
     */
    private static Date createExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 60 * 60 * 1000);
    }

    /**
     * 验证token是否有效
     *
     * @param token 客户端传入的token
     * @return 是否有效
     */
    public static boolean checkToken(String token) {
        return getUserIdFromToken(token) != null && !isTokenExpired(token);
    }

    /**
     * 检查token是否已失效
     *
     * @param token token
     * @return 是否失效
     */
    private static boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    private static Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据token获取userId
     *
     * @param token token值
     * @return userId
     */
    public static Long getUserIdFromToken(String token) {
        Long userId;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = Long.valueOf(claims.getSubject());
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    /**
     * 根据token获取claims
     *
     * @param token token
     * @return claims
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            Asserts.fail("JWT格式验证失败：{" + token + "}");
        }
        return claims;
    }

    public void setSecret(String secret) {
        JWTUtils.secret = secret;
    }

    public void setExpiration(Long expiration) {
        JWTUtils.expiration = expiration;
    }

}
