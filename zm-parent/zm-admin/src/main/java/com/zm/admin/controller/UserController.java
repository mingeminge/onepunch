package com.zm.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zm.admin.service.IUserService;
import com.zm.admin.vo.ResetPasswordVO;
import com.zm.admin.vo.UserVO;
import com.zm.common.contant.MethodConstant;
import com.zm.common.contant.ModuleConstant;
import com.zm.common.result.R;
import com.zm.log.annotation.LogAnnotation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/25 11:34
 * ==========================
 **/
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/list")
    @LogAnnotation(moduleName = ModuleConstant.USER_MODULE, methodName = MethodConstant.LIST)
    public R<IPage<UserVO>> list(Page<UserVO> iPage, UserVO user) {
        return R.data(userService.list(iPage, user));
    }

    @GetMapping("/{id}")
    @LogAnnotation(moduleName = ModuleConstant.USER_MODULE, methodName = MethodConstant.GET_ONE)
    public R<UserVO> findById(@PathVariable Integer id) {
        return R.data(userService.findById(id));
    }

    @PostMapping("/update")
    @LogAnnotation(moduleName = ModuleConstant.USER_MODULE, methodName = MethodConstant.UPDATE)
    public R<Boolean> update(@RequestBody UserVO userVO) {
        return R.status(userService.updateById(userVO));
    }

    @PostMapping("/setRole")
    @LogAnnotation(methodName = ModuleConstant.USER_MODULE, moduleName = MethodConstant.SET_ROLE)
    public R<Boolean> setRole(@RequestBody UserVO userVO) {
        return R.status(userService.setRole(userVO));
    }

    @PostMapping("/register")
    public R<Boolean> register(@RequestBody UserVO userVO) {
        return R.status(userService.register(userVO));
    }

    @DeleteMapping("/{id}")
    @LogAnnotation(moduleName = ModuleConstant.USER_MODULE, methodName = MethodConstant.DELETE)
    public R<Boolean> deleteUser(@PathVariable Integer id) {
        return R.status(userService.deleteUser(id));
    }

    @PostMapping("/resetPwd")
    @LogAnnotation(moduleName = ModuleConstant.USER_MODULE, methodName = MethodConstant.RESET_PWD)
    public R<Boolean> resetPassword(@RequestBody ResetPasswordVO resetPasswordVO) {
        return R.status(userService.resetPassword(resetPasswordVO));
    }
}
