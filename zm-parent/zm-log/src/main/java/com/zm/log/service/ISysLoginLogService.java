package com.zm.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zm.log.entity.SysLoginLog;
import org.apache.ibatis.annotations.Select;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 15:13
 * ==========================
 **/
public interface ISysLoginLogService extends IService<SysLoginLog> {
    @Select("select ")
    SysLoginLog getLastLogin(String username);
}
