package com.bh.filter;
import com.bh.security.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 授权过滤器
 *
 */
@Component
@Slf4j
public class TokenAuthFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;

    private RedisTemplate redisTemplate;

    public TokenAuthFilter(AuthenticationManager authenticationManager,
                           TokenManager tokenManager,
                           RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 获取档期那认证成功的用户权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        log.info("authRequest:{}",authRequest.toString());
        System.out.println("authRequest.toString()"+authRequest.toString());
        // 有权限信息 放入权限上下文中
        if (authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        chain.doFilter(request, response);
    }

    /**
     * 从token获取用户名,从redis获取对应权限列表,
     *
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        if (token == null) {
            return null;
        }
        //从token获取用户名
        String username = tokenManager.getUserInfoFromToken(token);
        log.info("username:{}",username);
        System.out.println("getAuthentication"+username.toString());
        //从redis获取对应权限列表
        List<String> list = (List<String>) redisTemplate.opsForValue().get(username);
        if (null == list) {
            return null;
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        list.forEach(item -> {
            authorities.add(new SimpleGrantedAuthority(item));
        });
        return new UsernamePasswordAuthenticationToken(username, token, authorities);

    }
}
