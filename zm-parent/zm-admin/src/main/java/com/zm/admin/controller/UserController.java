package com.zm.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zm.admin.service.IUserService;
import com.zm.admin.vo.UserVO;
import com.zm.common.result.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{name}")
    public R test(@PathVariable String name) {
        return R.data(userService.findByUsername(name));
    }

    @GetMapping("/list")
    public R<IPage<UserVO>> list(Page<UserVO> iPage, UserVO user) {
        return R.data(userService.list(iPage, user));
    }
}
