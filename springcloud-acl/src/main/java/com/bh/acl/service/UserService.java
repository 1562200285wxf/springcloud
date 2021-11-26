package com.bh.acl.service;

import com.bh.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface UserService extends IService<User> {
    /**
     * 从数据库中取出用户信息
     *
     * @param username
     * @return
     * @author lixiaolong
     * @date 2021/1/23 14:06
     */
    User selectByUsername(String username);
}
