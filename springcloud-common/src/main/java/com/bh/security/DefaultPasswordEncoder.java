package com.bh.security;

import com.bh.utils.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码处理工具类
 */
@Component
@Slf4j
public class DefaultPasswordEncoder implements PasswordEncoder {
    /**
     * 进行MD5加密
     */
    @Override
    public String encode(CharSequence rawPassword) {
        log.info("加密前{}",rawPassword);
        return MD5.encrypt(rawPassword.toString());
    }

    /*
    * springsecurity新版本增添了密码规则
    * 生成密码之后-----  前面加上     {密码类型}    比如：{MD5}96e79218965eb72c92a549dd5a330112
    * */
    public static void main(String[] args) {
        DefaultPasswordEncoder defaultPasswordEncoder = new DefaultPasswordEncoder();
        System.out.println(defaultPasswordEncoder.encode("111111"));
    }

    /**
     * 进行密码比对
     *
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.info("密码对比rawPassword:{}----encodedPassword:{}",rawPassword,encodedPassword);
        System.out.println("rawPassword----"+rawPassword+"-----encodedPassword--"+encodedPassword);
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }

}
