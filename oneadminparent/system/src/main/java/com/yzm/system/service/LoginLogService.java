package com.yzm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzm.system.entity.LoginLog;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 23:43 2019/12/19
 * ===========================
 */
public interface LoginLogService extends IService<LoginLog> {

    LoginLog getLastLogin(String username);
}
