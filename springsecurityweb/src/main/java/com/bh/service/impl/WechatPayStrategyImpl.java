package com.bh.service.impl;

import com.bh.common.emeu.PayEnum;
import com.bh.service.PayStrategy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/24 16:38
 * @Description: TODO
 */
@Service
public class WechatPayStrategyImpl implements PayStrategy {
    @Override
    public String pay(String type, String amount) {
        return String.format(MSG, PayEnum.WECHAT_PAY.getDescription(), amount);
    }
}