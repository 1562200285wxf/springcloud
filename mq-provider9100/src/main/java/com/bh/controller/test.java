package com.bh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/22 14:05
 * @Description: TODO
 */
@RestController
public class test {

    @GetMapping("/test")
    public ModelAndView te(){
        return new ModelAndView("redirect:http://localhost:9000/test");    }

    @CrossOrigin(origins = {"https://www.w3school.com.cn"})
    @GetMapping("/test1")
    public String test(){
        System.out.println("121");
        return "121";    }
}
