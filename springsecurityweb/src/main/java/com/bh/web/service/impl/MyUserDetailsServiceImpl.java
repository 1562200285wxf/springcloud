package com.bh.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bh.web.entity.CurrentUserInfo;
import com.bh.web.entity.Users;
import com.bh.web.mapper.UsersMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private PasswordEncoder passwordEncoder;

    private static Map<String, Users> usersMap= new ConcurrentHashMap<>();

    @Resource
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用usersMapper方法，根据用户名查询数据库
        //数据库没有用户名，认证失败
        if (!usersMap.containsKey(username)) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        Users users = usersMap.get(username);
        CurrentUserInfo curUser = new CurrentUserInfo();
        BeanUtils.copyProperties(users, curUser);

        List<GrantedAuthority> auths =
                AuthorityUtils.createAuthorityList("admin", "system", "ROLE_manager");

        return new User(users.getUsername(), passwordEncoder.encode(users.getPassword()), auths);
    }




    @PostConstruct
    public void getAllUser(){
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        List<Users> list = usersMapper.selectList(wrapper);
        usersMap =  list.stream().collect(Collectors.toMap(Users::getUsername, Function.identity()));
    }
}