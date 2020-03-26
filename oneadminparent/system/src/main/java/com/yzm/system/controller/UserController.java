package com.yzm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzm.common.constant.CoreConstant;
import com.yzm.common.util.CopyUtil;
import com.yzm.common.util.EasyExcelUtil;
import com.yzm.common.util.JsonUtil;
import com.yzm.common.vo.PageVO;
import com.yzm.common.vo.ResultVO;
import com.yzm.loging.annotation.LogAnnotation;
import com.yzm.system.controller.bo.UserBO;
import com.yzm.system.entity.User;
import com.yzm.system.controller.vo.ChangePassword;
import com.yzm.system.controller.vo.UserInfo;
import com.yzm.system.controller.vo.UserVO;
import com.yzm.system.query.UserQuery;
import com.yzm.common.util.RedisUtil;
import com.yzm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:30 2019/12/14
 * ===========================
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private RedisUtil redisUtil;

    private static final String MODULE_NAME = "用户管理";

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserController(UserService userService, RedisUtil redisUtil) {
        this.userService = userService;
        this.redisUtil = redisUtil;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:user:get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public PageVO list(UserQuery query) {
        return userService.queryList(query);
    }

    @GetMapping("/{id}")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    @PreAuthorize("hasAuthority('system:user:get')")
    public ResultVO findById(@PathVariable Long id) {
        UserVO userVO = userService.findById(id);
        return ResultVO.ok(userVO);
    }

    @GetMapping("/findByUsername/{username}")
    @PreAuthorize("hasAuthority('system:user:get')")
    public ResultVO findByUsername(@PathVariable String username) {
        User byUsername = userService.findByUsername(username);
        return ResultVO.ok(CopyUtil.copyProperties(byUsername, UserVO.class));
    }

    @PostMapping("/edit")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public ResultVO update(@RequestBody UserVO userVO) {
        Integer edit = userService.edit(userVO);
        if (edit == null) {
            return ResultVO.error();
        }
        return ResultVO.ok();
    }

    @PostMapping("/updateAvatar")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改头像")
    public ResultVO updateAvatar(@RequestBody UserVO userVO) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userVO.getId());
        User one = userService.getOne(wrapper);
        if (null != one) {
            one.setAvatar(userVO.getAvatar());
            one.setUpdateTime(new Date());
            boolean update = userService.updateById(one);
            if (update) {
                return ResultVO.ok();
            }
        }
        return ResultVO.error();
    }

    @PostMapping("/updateInfo")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改")
    public ResultVO updateInfo(@RequestBody UserVO userVO) {
        User user = userService.getById(userVO.getId());
        user.setPhone(userVO.getPhone());
        user.setEmail(userVO.getEmail());
        user.setSex(userVO.getSex());
        user.setUpdateTime(new Date());
        boolean update = userService.updateById(user);
        if (update) {
            return ResultVO.ok();
        }
        return ResultVO.error();
    }

    @PostMapping("/changePassword")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改密码")
    public ResultVO changePassword(@Valid @RequestBody ChangePassword changePassword, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error("参数不合法");
        }
        User user = userService.getById(changePassword.getId());
        if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            return ResultVO.error("密码错误");
        }
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        user.setUpdateTime(new Date());
        boolean update = userService.updateById(user);
        if (update) {
            return ResultVO.ok();
        }
        return ResultVO.error();
    }


    @PostMapping("/insert")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "新增")
    public ResultVO insert(@RequestBody UserVO userVO) {
        Integer insert = userService.insert(userVO);
        if (null == insert) {
            return ResultVO.error();
        }
        return ResultVO.ok();
    }

    @PostMapping("/delete")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除")
    @PreAuthorize("hasAuthority('system:user:delete')")
    public ResultVO deleteUser(@RequestBody UserVO userVO) {
        Integer delete = userService.delete(userVO);
        if (delete == null) {
            return ResultVO.error();
        }
        return ResultVO.ok();

    }

    @GetMapping("/onlineList")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "在线用户")
    public ResultVO onlineList() {
        Set<String> keys = redisUtil.keys(CoreConstant.TOKEN_PREFIX + "*");
        List<UserInfo> userInfoList = new ArrayList<>();
        for (String key : keys) {
            String userInfoJson = redisUtil.get(key);
            UserInfo userInfo = JsonUtil.jsonToPojo(userInfoJson, UserInfo.class);
            userInfoList.add(userInfo);
        }
        return ResultVO.ok(userInfoList);
    }

    @PostMapping("/kickout")
    @PreAuthorize("hasAuthority('system:user:delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "强制下线")
    public ResultVO kickout(@RequestBody UserInfo userInfo) {
        redisUtil.deleteKey(userInfo.getToken());
        return ResultVO.ok();
    }

    @GetMapping("/info")
    public ResultVO userInfo(HttpServletRequest request) {
        String token = request.getHeader(CoreConstant.TOKEN_HEADER);
        String userJson = redisUtil.get(token);
        UserInfo userInfo = JsonUtil.jsonToPojo(userJson, UserInfo.class);
        return ResultVO.ok(userInfo);
    }

    @GetMapping("/export")
    public void test(HttpServletResponse response) throws IOException {
        List<User> list = userService.list();
        List<UserBO> data = CopyUtil.copyList(list, UserBO.class);
        String fileName = System.currentTimeMillis() + ".xlsx";
        EasyExcelUtil<UserBO> excelUtil = new EasyExcelUtil<>();
        excelUtil.export(fileName, "测试",data, UserBO.class, response);
    }
}
