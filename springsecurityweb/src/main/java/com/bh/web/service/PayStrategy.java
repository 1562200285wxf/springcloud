package com.bh.web.service;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/24 16:35
 * @Description: TODO
 */
public interface PayStrategy {

    String MSG = "使用 %s ,消费了 %s 元";

    String pay(String type, String amount);
}
