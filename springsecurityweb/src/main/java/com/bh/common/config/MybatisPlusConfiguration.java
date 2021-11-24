package com.bh.common.config;

import com.bh.mapper.self.CustomizedSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/24 17:23
 * @Description: TODO
 */
@Configuration
public class MybatisPlusConfiguration {
    @Bean
    public CustomizedSqlInjector customizedSqlInjector() {
        return new CustomizedSqlInjector();
    }

}
