package com.bh.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token操作工具类
 */

@Component
@Slf4j
public class TokenManager {
    //token有效时长 24小时
    private static long tokenEcpiration = 24 * 60 * 60 * 1000;
    //编码秘钥
    private static String tokenSignKey = "123456";

    //1 使用jwt根据用户名生成token
    public String createToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenEcpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        log.info("登陆用户token{}",token);
        System.out.println("-------使用jwt根据用户名生成token"+token);
        return token;
    }

    public static void main(String[] args) {
        TokenManager tokenManager = new TokenManager();
        System.out.println(tokenManager.createToken("王孝峰"));
        System.out.println(tokenManager.getUserInfoFromToken(tokenManager.createToken("王孝峰")));
    }

    //2 根据token字符串得到用户信息
    public String getUserInfoFromToken(String token) {
        String result = Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token).getBody().getSubject();
        log.info("token字符串得到用户信息{}",result);
        System.out.println("token字符串得到用户信息"+result);
        return result;
    }

    //3 删除token---客户端不携带token，
    public void removeToken(String token) {
    }
}
