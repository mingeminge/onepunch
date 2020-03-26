package com.yzm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzm.common.constant.CoreConstant;
import com.yzm.common.enums.ResultEnum;
import com.yzm.common.util.FileUtil;
import com.yzm.common.vo.PageVO;
import com.yzm.common.vo.ResultVO;
import com.yzm.system.entity.LoginSetting;
import com.yzm.system.query.SettingQuery;
import com.yzm.system.service.impl.LoginSettingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:24 2019/12/27
 * ===========================
 */
@RestController
@RequestMapping("/setting")
@Slf4j
public class LoginSettingController {

    private LoginSettingServiceImpl loginSettingService;

    private FileUtil fileUtil;

    private static final String UPLOAD_PATH = "/image/login_bg/";

    private static final String ROOT_PATH = "/data/sftp/upload";

    @Autowired
    public LoginSettingController(LoginSettingServiceImpl loginSettingService, FileUtil fileUtil) {
        this.loginSettingService = loginSettingService;
        this.fileUtil = fileUtil;
    }

    @GetMapping("/bg")
    public ResultVO getSetting() {
        Map<String, Object> setting = new HashMap<>(3);
        List<LoginSetting> bgList = loginSettingService.list();
        for (LoginSetting loginSetting : bgList) {
            if (loginSetting.getIsOn()) {
                if (loginSetting.getType().equals(CoreConstant.LOGIN_BG)) {
                    setting.put("bg", loginSetting.getUrl());
                } else if (loginSetting.getType().equals(CoreConstant.LOGIN_GIF)) {
                    setting.put("gif", loginSetting.getUrl());
                    setting.put("time",loginSetting.getTime());
                }

            }
        }
        return ResultVO.ok(setting);
    }

    @GetMapping("/list")
    public PageVO list(SettingQuery query) {
        Page<LoginSetting> page = new Page<>(query.getCurrent(), query.getSize());
        QueryWrapper<LoginSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("type", query.getType());
        Page<LoginSetting> avatarPage = loginSettingService.page(page, wrapper);
        return PageVO.build(avatarPage.getTotal(), avatarPage.getRecords());
    }

    @PostMapping("/update")
    public ResultVO update(@RequestBody LoginSetting loginSetting) {
        QueryWrapper<LoginSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("type", loginSetting.getType());
        List<LoginSetting> list = loginSettingService.list(wrapper);
        List<LoginSetting> updateList = new ArrayList<>();
        for (LoginSetting setting : list) {
            if (!setting.getId().equals(loginSetting.getId())) {
                setting.setIsOn(false);
            } else {
                setting.setIsOn(true);
            }
            setting.setTime(loginSetting.getTime());
            updateList.add(setting);
        }
        boolean update = loginSettingService.updateBatchById(updateList);
        if (update) {
            return ResultVO.ok();
        }
        return ResultVO.error(ResultEnum.SYSTEM_ERROR);
    }

    @PostMapping("/{id}")
    public ResultVO delete(@PathVariable Long id) {
        LoginSetting loginSetting = loginSettingService.getById(id);
        try {
            fileUtil.deleteFile(ROOT_PATH + "/image/" + loginSetting.getUrl());
            boolean delete = loginSettingService.removeById(id);
            if (delete) {
                return ResultVO.ok();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除文件{}失败", loginSetting.getUrl());
        }
        return ResultVO.error(ResultEnum.SYSTEM_ERROR);
    }

    @PostMapping("/upload")
    public ResultVO upload(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResultVO.error(ResultEnum.SYSTEM_ERROR);
            }
            String fileName = file.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String path = System.currentTimeMillis() + fileType;
            boolean upload = fileUtil.uploadFile(UPLOAD_PATH + path, file.getInputStream());
            if (upload) {
                return ResultVO.ok("/login_bg/" + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(ResultEnum.SYSTEM_ERROR);
        }
        return ResultVO.error(ResultEnum.SYSTEM_ERROR);
    }

    @PostMapping("/save")
    public ResultVO save(@Validated @RequestBody LoginSetting loginSetting, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(result.getFieldError().getDefaultMessage());
        }
        boolean save = loginSettingService.save(loginSetting);
        if (save) {
            return ResultVO.ok();
        }
        return ResultVO.error(ResultEnum.SYSTEM_ERROR);
    }
}
