package com.bh.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token操作工具类
 */

@Component
public class TokenManager {
    //token有效时长 24小时
    private long tokenEcpiration = 24 * 60 * 60 * 1000;
    //编码秘钥
    private String tokenSignKey = "123456";

    //1 使用jwt根据用户名生成token
    public String createToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenEcpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();

        return token;
    }

    //2 根据token字符串得到用户信息
    public String getUserInfoFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

    //3 删除token
    public void removeToken(String token) {
    }
}
