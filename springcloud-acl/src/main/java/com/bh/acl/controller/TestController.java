package com.bh.acl.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/25 19:03
 * @Description: TODO
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Resource
    RedisTemplate redisTemplate;

    @GetMapping("/test1")
    public void test(){
        redisTemplate.opsForValue().set("name","王孝峰");
    }
}
