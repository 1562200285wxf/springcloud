package com.bh.web.service.impl;

import com.bh.web.common.emeu.PayEnum;
import com.bh.web.service.PayStrategy;
import org.springframework.stereotype.Service;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/24 16:40
 * @Description: TODO
 */
@Service
public class UnionPayStrategyImpl implements PayStrategy {
    @Override
    public String pay(String type, String amount) {
        return String.format(MSG, PayEnum.UNION_PAY.getDescription(), amount);
    }
}