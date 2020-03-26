package com.yzm.loging.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzm.common.vo.PageVO;
import com.yzm.common.vo.ResultVO;
import com.yzm.loging.entity.SystemLog;
import com.yzm.loging.query.SystemLogQuery;
import com.yzm.loging.service.SystemLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:16 2019/12/19
 * ===========================
 */
@RestController
@RequestMapping("/systemLog")
public class SystemLogController {

    @Autowired
    private SystemLogService logService;

    @GetMapping("/list")
    public PageVO list(SystemLogQuery query) {
        Page<SystemLog> page = new Page<>(query.getCurrent(), query.getSize());
        QueryWrapper<SystemLog> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getUsername())) {
            wrapper.like("username", query.getUsername());
        }
        if (null != query.getErrorFlag()) {
            wrapper.eq("error_flag", query.getErrorFlag());
        }
        if (null != query.getStartTime() && null != query.getEndTime()) {
            wrapper.ge("create_time", query.getStartTime());
            wrapper.le("create_time", query.getEndTime());
        }
        wrapper.orderByDesc("create_time");
        Page<SystemLog> systemLogPage = logService.page(page, wrapper);
        return PageVO.build(systemLogPage.getTotal(), systemLogPage.getRecords());

    }

    @PostMapping("/delete")
    public ResultVO delete(@RequestBody SystemLog systemLog) {
        boolean removeById = logService.removeById(systemLog.getId());
        if (removeById) {
            return ResultVO.ok();
        }
        return ResultVO.error();

    }
}
