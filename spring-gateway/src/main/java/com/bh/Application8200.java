package com.bh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/19 15:48
 * @Description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application8200 {
    public static void main(String[] args) {
        SpringApplication.run(Application8200.class,args);
    }
}
