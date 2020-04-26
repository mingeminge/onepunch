package com.yzm.system.service;

import javax.jms.Destination;

/**
 * ==================================
 *
 * @author yizuomin
 * @description:
 * @date 2020/4/26 09:19
 * ==================================
 **/
public interface MqService {

    void sendMq(Destination destination, Object str);
}
