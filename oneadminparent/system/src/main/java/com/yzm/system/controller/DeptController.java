package com.yzm.system.controller;

import com.yzm.common.util.CopyUtil;
import com.yzm.common.vo.ResultVO;
import com.yzm.loging.annotation.LogAnnotation;
import com.yzm.system.entity.Dept;
import com.yzm.system.controller.vo.DeptVO;
import com.yzm.system.query.DeptQuery;
import com.yzm.system.service.DeptService;
import com.yzm.system.service.dto.DeptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:48 2019/12/15
 * ===========================
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    private DeptService deptService;

    private static final String MODULE_NAME = "部门管理";

    @Autowired
    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:dept:get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public ResultVO list(DeptQuery query) {
        List<DeptDto> deptDtos = deptService.queryList(query);
        return ResultVO.ok(deptService.buildTree(deptDtos));
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('system:dept:insert')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "新增")
    public ResultVO insert(@RequestBody DeptVO deptVO) {
        Dept dept = CopyUtil.copyProperties(deptVO, Dept.class);
        dept.setCreateTime(new Date());
        boolean save = deptService.save(dept);
        if (save) {
            return ResultVO.ok();
        }
        return ResultVO.error();
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('system:dept:edit')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改")
    public ResultVO edit(@RequestBody DeptVO deptVO) {
        Dept dept = CopyUtil.copyProperties(deptVO, Dept.class);
        dept.setUpdateTime(new Date());
        boolean update = deptService.updateById(dept);
        if (update) {
            return ResultVO.ok();
        }
        return ResultVO.error();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dept:get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public ResultVO findById(@PathVariable Long id) {
        Dept byId = deptService.getById(id);
        DeptVO deptVO = CopyUtil.copyProperties(byId, DeptVO.class);
        return ResultVO.ok(deptVO);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:dept:delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除")
    public ResultVO delete(@RequestBody DeptVO deptVO) {
        Integer delete = deptService.delete(deptVO.getId());
        if (null != delete) {
            return ResultVO.ok();
        }
        return ResultVO.error();
    }
}
