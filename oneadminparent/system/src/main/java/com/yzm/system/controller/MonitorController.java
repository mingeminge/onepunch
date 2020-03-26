package com.yzm.system.controller;

import com.yzm.common.vo.ResultVO;
import com.yzm.system.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:36 2019/12/24
 * ===========================
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/serverInfo")
    public ResultVO getServerInfo() {
        return ResultVO.ok(monitorService.getServerInfoVO());
    }

    @GetMapping("/loginLog")
    public ResultVO getLoginLog() {
        return ResultVO.ok();
    }
}
