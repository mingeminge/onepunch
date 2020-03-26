package com.yzm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.system.entity.Message;
import com.yzm.system.mapper.MessageMapper;
import com.yzm.system.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:12 2020/2/10
 * ===========================
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
