package com.bh.controller;

import com.bh.vo.mq.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/12 13:42
 * @Description: TODO
 */
@RestController
@RequestMapping("/provider")
public class provider {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Value("${active.queue.name}")
    String queue ;

    @Value("${active.queue-stu.name}")
    String queuestu ;
    @Value("${active.topic.name}")
    String topic ;

    @GetMapping("/queue/{str}")
    public void queue(String str){
        System.out.println("11");
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(str);
                return message;
            }
        });
    }

    @GetMapping("/queue1")
    public void topic(String str){
        jmsMessagingTemplate.convertAndSend(queue,"str");
    }

}
