package com.bh.web.controller;

import com.bh.web.common.utils.PayStrategyFactory;
import com.bh.web.entity.Users;
import com.bh.web.mapper.UsersMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/24 16:43
 * @Description: TODO
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    UsersMapper usersMapper;

    @GetMapping("/test")
    public String test(String type){
        return PayStrategyFactory.getPayStrategy(type).pay(type,"12");
    }

    @GetMapping("/test1")
    public void test1(){
        usersMapper.insertBatch(Arrays.asList(new Users(111,"qw","qw")));
    }
}
