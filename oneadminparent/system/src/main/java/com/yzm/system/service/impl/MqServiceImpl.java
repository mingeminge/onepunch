package com.yzm.system.service.impl;

import com.yzm.system.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * ==================================
 *
 * @author yizuomin
 * @description:
 * @date 2020/4/26 09:20
 * ==================================
 **/
@Service
public class MqServiceImpl implements MqService {

    private final JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private MqServiceImpl(JmsMessagingTemplate jmsMessagingTemplate){
        this.jmsMessagingTemplate=jmsMessagingTemplate;
    }


    @Override
    public void sendMq(Destination destination, Object str) {
        jmsMessagingTemplate.convertAndSend(destination,str);
    }
}
