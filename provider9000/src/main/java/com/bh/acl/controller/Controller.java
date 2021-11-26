package com.bh.acl.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/10/28 16:33
 * @Description: TODO
 */
@RestController("/provider9000")
public class Controller {

    private static String target = "target";
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @Transactional(rollbackFor=Exception.class)
    public String test()  {
        System.out.println("1");
        try{
            System.out.println("2");
            int q = 1/0;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(target);
        }
        System.out.println("5");
        return target ;
    }
}
