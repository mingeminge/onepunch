package com.yzm.system.controller;

import com.yzm.common.vo.PageVO;
import com.yzm.common.vo.ResultVO;
import com.yzm.loging.annotation.LogAnnotation;
import com.yzm.system.entity.Role;
import com.yzm.system.controller.vo.RoleVO;
import com.yzm.system.query.RoleQuery;
import com.yzm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:18 2019/12/14
 * ===========================
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleService roleService;

    private static final String MODULE_NAME = "角色管理";

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('system:role:get')")
    public ResultVO list() {
        List<Role> roleList = roleService.list();
        return ResultVO.ok(roleList);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:role:get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public PageVO list(RoleQuery query) {
        return roleService.queryList(query);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public ResultVO findById(@PathVariable Long id) {
        return ResultVO.ok(roleService.findById(id));
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('system:role:edit')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改")
    public ResultVO edit(@RequestBody RoleVO roleVO) {
        Integer edit = roleService.edit(roleVO);
        if (null == edit) {
            return ResultVO.error();
        }
        return ResultVO.ok();
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('system:role:insert')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "新增")
    public ResultVO insert(@RequestBody RoleVO roleVO) {
        Integer insert = roleService.insert(roleVO);
        if (null == insert) {
            return ResultVO.error();
        }
        return ResultVO.ok();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:role:delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除")
    public ResultVO delete(@RequestBody RoleVO roleVO) {
        Integer delete = roleService.delete(roleVO);
        if (null == delete) {
            return ResultVO.error();
        }
        return ResultVO.ok();
    }
}
