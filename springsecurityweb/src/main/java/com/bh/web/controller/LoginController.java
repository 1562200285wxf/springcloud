package com.bh.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bh.web.entity.Users;
import com.bh.web.mapper.UsersMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/15 17:56
 * @Description: TODO
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    UsersMapper usersMapper;

    @GetMapping("/select")
    public String select(){
        LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Users::getId,1);
        return usersMapper.selectList(lambdaQueryWrapper).toString();
    }

    @GetMapping("/hello")
    public String hello(){
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authenticationToken.getPrincipal();
        System.out.println(userDetails);
        System.out.println("hello");
        return "hello";
    }

    @GetMapping("/login")
    public void login(){
        System.out.println("login");
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(){
        System.out.println("loginSuccess");
        return "loginSuccess";
    }

    @GetMapping("/admin")
    public String admin(){
        System.out.println("admin");
        return "admin";
    }

    @GetMapping("/secure")
    @Secured("ROLE_manager")
    public String secured(){
        System.out.println("secured");
        return "secured";
    }

    /*
    *  PreAuthorize  ??????????????????  ???????????????
    *  hasAnyAuthority ???????????????    hasAuthority ????????????
    *  hasAnyRole      hasRole
    *  PostAuthorize  ??????????????????
    * */
    @GetMapping("/preauth")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String rreAuthorize(){
        System.out.println();
        return "PreAuthorize";
    }
}