package com.bh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/19 16:17
 * @Description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application8201 {
    public static void main(String[] args) {
        SpringApplication.run(Application8201.class,args);
    }
}
