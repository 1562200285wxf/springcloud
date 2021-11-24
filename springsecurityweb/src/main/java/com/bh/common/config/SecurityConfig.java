package com.bh.common.config;

import com.bh.service.impl.MyUserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/15 18:31
 * @Description: TODO
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    PersistentTokenRepository persistentTokenRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/user/login") // 点击登陆请求的地址
                .defaultSuccessUrl("/user/loginSuccess").permitAll() // 通过登陆界面认证后的默认跳转界面
                // 哪些路径不需要登陆
                .and()
                .authorizeRequests().antMatchers("/", "/user/hello", "/user/login", "/test/*").permitAll()//免登录接口
//               权限和角色都可以在接口注解实现
//                .antMatchers("/user/admin").hasAuthority("admin")//只有拥有admin权限，才能登陆接口,只有一个参数
//                .antMatchers("/user/admins").hasAnyAuthority("admin","system")
//                .antMatchers("/user/admins").hasAnyRole("student")
                .anyRequest().authenticated()  //  所有的接口都需要认证登陆
                .and()
                // 设置 过期时间 用于记住我 自动登陆
                .rememberMe()
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(6000)
                .userDetailsService(userDetailsService)
                .and()
                .csrf().disable();//禁用默认的登陆界面

                /*
         自定义无访问权限跳转的页面
         */
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        /*
        logoutUrl("/logout") 退出路径---界面中写这个，会自动导向到此路由
        logoutSuccessUrl("/test/hello") 退出成功后跳转到哪个路径
         */
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/user/hello").permitAll();
    }
}
