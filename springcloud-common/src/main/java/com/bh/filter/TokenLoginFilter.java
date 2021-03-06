package com.bh.filter;

import com.bh.entity.CurrentUserInfo;
import com.bh.entity.SecurityUser;
import com.bh.security.TokenManager;
import com.bh.utils.R;
import com.bh.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 认证过滤器 生成token 放到redis中
 */
@Component
@Slf4j
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 权限
     */
    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        // 设置登陆路径，并且post请求
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login", "POST"));
    }

    /**
     * 获取表单提交的相关信息
     * @throws AuthenticationException
     * @author 王孝峰
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        // 获取表单提交数据
        try {
            CurrentUserInfo user = new ObjectMapper().readValue(request.getInputStream(), CurrentUserInfo.class);
            //会调用userdetail 查数据库
            Authentication result = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword(), new ArrayList<>()
                    )
            );
            log.info("CurrentUserInfo{}:",user.toString());
            log.info("Authentication{}:",result.toString());
            System.out.println("attemptAuthentication--user-------"+user);
            System.out.println("attemptAuthentication---result------"+result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * Default behaviour for successful authentication.
     * 认证成功调用的方法 生成token 存入到redis
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // 认证成功之后，得到认证成功后的用户信息
        SecurityUser user = (SecurityUser) authResult.getPrincipal();
        log.info("SecurityUser{}:",user.toString());
        System.out.println("successfulAuthentication-----------------"+user.toString());
        // 根据用户名生成token
        String token = tokenManager.createToken(user.getUsername());

        // 把用户名和用户权限放入redis中
        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(), user.getPermissionValueList());

        ResponseUtil.out(response, R.ok().data("token", token));
    }

    /**
     * Default behaviour for unsuccessful authentication.
     * 认证失败调用的方法
     *
     * @param request
     * @param response
     * @param failed
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, R.error());
    }
}