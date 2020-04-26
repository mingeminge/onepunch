package com.yzm.system.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * ==================================
 *
 * @author yizuomin
 * @description: queue消费者
 * @date 2020/4/26 09:16
 * ==================================
 **/
@Component
public class QueueConsumer {

    /**
     * @param message 消息
     */
    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "queueListener")
    public void readMq(String message) {
        System.out.println("queue接收到：" + message);
    }


    /**
     * @param message 消息
     */
    @JmsListener(destination = "${spring.activemq.topic-name}", containerFactory = "topicListener")
    public void readActiveQueue(String message) {
        System.out.println("topic接受到：" + message);
    }
}
