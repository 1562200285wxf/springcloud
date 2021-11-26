package com.bh.acl.service.impl;

import com.bh.entity.User;
import com.bh.acl.service.PermissionService;
import com.bh.acl.service.UserService;
import com.bh.entity.CurrentUserInfo;
import com.bh.entity.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    /**
     * 根据用户名 获取用户权限
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询数据
        User user = userService.selectByUsername(username);
        //判断
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        CurrentUserInfo curUser = new CurrentUserInfo();
        BeanUtils.copyProperties(user, curUser);

        log.info("--------------------user--load{}",user.toString());
        System.out.println("user--load{}"+user.toString());
        //根据用户查询用户权限列表
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());

        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(permissionValueList);
        return securityUser;
    }
}
