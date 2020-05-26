package com.zm.admin.controller;

import com.zm.admin.entity.Role;
import com.zm.admin.service.IRoleService;
import com.zm.admin.vo.RoleVO;
import com.zm.admin.wrapper.RoleWrapper;
import com.zm.common.contant.MethodConstant;
import com.zm.common.contant.ModuleConstant;
import com.zm.common.result.R;
import com.zm.common.utils.CopyUtil;
import com.zm.log.annotation.LogAnnotation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/26 10:11
 * ==========================
 **/
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("/list")
    @LogAnnotation(moduleName = ModuleConstant.ROLE_MODULE, methodName = MethodConstant.LIST)
    public R<List<RoleVO>> list() {
        List<Role> list = roleService.list();
        return R.data(RoleWrapper.build().listVO(list));
    }

    @GetMapping("/{id}")
    @LogAnnotation(moduleName = ModuleConstant.ROLE_MODULE, methodName = MethodConstant.GET_ONE)
    public R<RoleVO> findById(@PathVariable Integer id) {
        Role role = roleService.getById(id);
        RoleVO roleVO = RoleWrapper.build().entityVO(role);
        return R.data(roleVO);
    }

    @PostMapping("/update")
    @LogAnnotation(moduleName = ModuleConstant.ROLE_MODULE, methodName = MethodConstant.UPDATE)
    public R<Boolean> update(@RequestBody RoleVO roleVO) {
        Role role = CopyUtil.copyProperties(roleVO, Role.class);
        return R.status(roleService.updateById(role));
    }

    @PostMapping("/setMenu")
    @LogAnnotation(moduleName = ModuleConstant.ROLE_MODULE, methodName = MethodConstant.SET_MENU)
    public R<Boolean> setMenu(@RequestBody RoleVO roleVO) {
        return R.status(roleService.setMenu(roleVO));
    }

    @PostMapping("/save")
    @LogAnnotation(moduleName = ModuleConstant.ROLE_MODULE, methodName = MethodConstant.SAVE)
    public R<Boolean> save(@RequestBody RoleVO roleVO) {
        Role role = CopyUtil.copyProperties(roleVO, Role.class);
        return R.status(roleService.save(role));
    }

    @DeleteMapping("/delete")
    @LogAnnotation(moduleName = ModuleConstant.ROLE_MODULE, methodName = MethodConstant.DELETE)
    public R<Boolean> delete(@RequestBody RoleVO roleVO) {
        return R.status(roleService.delete(roleVO));
    }

}
