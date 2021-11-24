package com.bh.common.utils;

import com.bh.common.emeu.PayEnum;
import com.bh.common.utils.SpringContextUtil;
import com.bh.service.PayStrategy;
import com.bh.service.impl.AliPayStrategyImpl;
import org.springframework.stereotype.Component;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/24 16:50
 * @Description: TODO
 */
public class PayStrategyFactory {

    /**
     * 根据type获取对应PayStrategy实现类
     * @param type
     * @return
     */
    public static PayStrategy getPayStrategy(String type){
        PayEnum payEnum = PayEnum.getByType(type);
        if (payEnum == null){
            return null;
        }
        return SpringContextUtil.getBean(payEnum.getBeanName(), PayStrategy.class);
//        return SpringContextUtil.getBean(payEnum.getBeanName(), PayStrategy.class);
    }
}
