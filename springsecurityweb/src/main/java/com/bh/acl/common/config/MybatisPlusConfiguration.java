package com.bh.acl.common.config;

import com.bh.mapper.self.CustomizedSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
