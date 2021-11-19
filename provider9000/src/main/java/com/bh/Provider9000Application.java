package com.bh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/12 10:37
 * @Description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Provider9000Application {

    public static void main(String[] args) {
        SpringApplication.run(Provider9000Application.class, args);
    }


}