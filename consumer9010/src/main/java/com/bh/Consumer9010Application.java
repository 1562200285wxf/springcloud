package com.bh;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/12 10:47
 * @Description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Consumer9010Application {

    @Value("${name}")
    String name;

    public static void main(String[] args) {
        SpringApplication.run(Consumer9010Application.class, args);
    }


}