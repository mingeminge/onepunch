package com.zm.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.log.entity.SysLoginLog;
import com.zm.log.mapper.SysLoginLogMapper;
import com.zm.log.service.ISysLoginLogService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 15:14
 * ==========================
 **/
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {
    @Override
    public SysLoginLog getLastLogin(String username) {
        return null;
    }
}
