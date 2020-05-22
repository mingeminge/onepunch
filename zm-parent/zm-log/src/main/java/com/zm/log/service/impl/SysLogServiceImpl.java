package com.zm.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.log.entity.SysLog;
import com.zm.log.mapper.SysLogMapper;
import com.zm.log.service.ISysLogService;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 15:13
 * ==========================
 **/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {
}
