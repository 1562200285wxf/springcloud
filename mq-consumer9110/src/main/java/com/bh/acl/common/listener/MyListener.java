package com.bh.acl.common.listener;

import bis.api.sdksign.CreateH5AuthRequest;
import bis.api.sdksign.CreateH5AuthResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/7 21:01
 * @Description: TODO
 */
@Component
public class MyListener {

    private String queuename = "queue:bis.business.h5.faceid.WB";
    @JmsListener(destination = "${active.queue.name}")
    public void listener(Message message){
        if(message instanceof TextMessage){
            System.out.println(message);

        }
    }

    @JmsListener(destination = "bis.business.h5.faceid.WB")
    public CreateH5AuthResponse listener1(CreateH5AuthRequest createH5AuthRequest){
        System.out.println(JSONObject.toJSONString(createH5AuthRequest));
        return new CreateH5AuthResponse();
    }
//    @JmsListener(destination = "queue_wang")
//    public void listener(String  str){
//        System.out.println(str);
//    }
}
