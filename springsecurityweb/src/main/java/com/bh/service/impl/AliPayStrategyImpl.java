package com.bh.service.impl;

import com.bh.acl.common.emeu.PayEnum;
import com.bh.service.PayStrategy;
import org.springframework.stereotype.Service;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/24 16:36
 * @Description: TODO
 */
@Service
public class AliPayStrategyImpl implements PayStrategy {

    @Override
    public String pay(String type, String amount) {
        return String.format(MSG, PayEnum.ALI_PAY.getDescription(), amount);
    }
}