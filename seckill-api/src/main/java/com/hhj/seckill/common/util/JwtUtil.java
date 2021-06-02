package com.hhj.seckill.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/6/1 15:06
 * @Version 1.0
 */
@Component
@Slf4j
public class JwtUtil {
    // 密钥
    private final String SECTET="zrf5202e34348f86b67cde581c0f9eb5";
    // 过期时间
    private final long EXPIRE =  604800;
    // 请求头
    private String HEADER = "SEC_TOKEN";

    /**
     * 生成JWT
     * @param userId
     * @return
     */
    public String generateToken(Integer userId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE * 10000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECTET)
                .compact();
    }

    public Claims getClaimByToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECTET)
                    .parseClaimsJws(token)
                    .getBody();
            return body;
        }catch (Exception e){
            log.error("用户尚未登录/token异常", e);
            return null;
        }

    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
