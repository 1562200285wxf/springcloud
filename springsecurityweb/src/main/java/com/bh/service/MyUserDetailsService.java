package com.bh.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bh.entity.Users;
import com.bh.mapper.UsersMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    private PasswordEncoder passwordEncoder;

    private static Map<String,Users> usersMap= new ConcurrentHashMap<>();

    @Resource
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用usersMapper方法，根据用户名查询数据库
//        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Users::getUsername, username);
//        Users users = usersMapper.selectOne(wrapper);
        //数据库没有用户名，认证失败
        if (!usersMap.containsKey(username)) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        Users users = usersMap.get(username);
        List<GrantedAuthority> auths =
                AuthorityUtils.createAuthorityList("admin", "system", "ROLE_manager");

//        return new User(users.getUsername(),users.getPassword(),auths);
        return new User(users.getUsername(), passwordEncoder.encode(users.getPassword()), auths);
    }

    @PostConstruct
    public void getAllUser(){
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        List<Users> list = usersMapper.selectList(wrapper);
        usersMap =  list.stream().collect(Collectors.toMap(Users::getUsername, Function.identity()));
    }
}