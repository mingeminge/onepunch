package com.yzm.system.service.impl;

import com.yzm.common.util.CpuUtil;
import com.yzm.common.util.OsUtil;
import com.yzm.common.util.RedisUtil;
import com.yzm.system.controller.vo.ServerInfoVO;
import com.yzm.system.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:55 2019/12/24
 * ===========================
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ServerInfoVO getServerInfoVO() {
        CpuUtil cpuUtil = CpuUtil.getInstance();
        cpuUtil.getProcessCpu();
        ServerInfoVO infoVO = new ServerInfoVO(OsUtil.getOsName(), cpuUtil.getProcessCpu(),
                OsUtil.getProcessCpuLoad(), OsUtil.getTotalMemorySize(),
                OsUtil.getFreePhysicalMemorySize(), OsUtil.getUsedMemory(), redisUtil.keys("*").size(), new Date());
        return infoVO;
    }
}
