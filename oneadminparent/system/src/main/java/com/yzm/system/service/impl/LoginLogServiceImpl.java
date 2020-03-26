package com.yzm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.system.entity.LoginLog;
import com.yzm.system.mapper.LoginLogMapper;
import com.yzm.system.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 23:44 2019/12/19
 * ===========================
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public LoginLog getLastLogin(String username) {
        return loginLogMapper.getLastLogin(username);
    }
}
