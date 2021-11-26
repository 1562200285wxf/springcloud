package com.bh.config;

import com.bh.filter.TokenAuthFilter;
import com.bh.filter.TokenLoginFilter;
import com.bh.security.DefaultPasswordEncoder;
import com.bh.security.TokenLogoutHandler;
import com.bh.security.TokenManager;
import com.bh.security.UnauthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/23 16:35
 * @Description: TODO
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private DefaultPasswordEncoder defaultPasswordEncoder;
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService,
                                  DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager,
                                  RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    //设置退出的地址和token，redis操作地址
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                //没有权限访问时调用自定义的处理类
                .authenticationEntryPoint(new UnauthEntryPoint())
                .and()
//                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                //退出路径
                .and()
                .logout().logoutUrl("/admin/acl/index/logout")
                // 调用退出时的处理器
                .addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate))
                .and()
                // 认证过滤器
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                // 授权过滤器
                .addFilter(new TokenAuthFilter(authenticationManager(), tokenManager, redisTemplate))
                .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**","*");
    }
}