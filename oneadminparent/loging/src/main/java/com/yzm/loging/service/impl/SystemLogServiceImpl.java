package com.yzm.loging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.loging.entity.SystemLog;
import com.yzm.loging.mapper.SystemLogMapper;
import com.yzm.loging.service.SystemLogService;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:56 2019/12/19
 * ===========================
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements SystemLogService {
}
