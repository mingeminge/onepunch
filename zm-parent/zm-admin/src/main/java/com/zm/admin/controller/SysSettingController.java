package com.zm.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zm.admin.entity.SysSetting;
import com.zm.admin.service.ISysSettingService;
import com.zm.common.contant.FileConstant;
import com.zm.common.contant.MethodConstant;
import com.zm.common.contant.ModuleConstant;
import com.zm.common.enums.ResultEnum;
import com.zm.common.result.R;
import com.zm.common.utils.SftpUtil;
import com.zm.log.annotation.LogAnnotation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/26 11:11
 * ==========================
 **/
@RestController
@RequestMapping("/setting")
@AllArgsConstructor
@Slf4j
public class SysSettingController {

    private static final Integer ACTIVE_STATUS = 1;

    private final SftpUtil sftpUtil;

    private final ISysSettingService sysSettingService;

    @GetMapping("/getSetting")
    @LogAnnotation(moduleName = ModuleConstant.SYS_SETTING_MODULE, methodName = MethodConstant.GET_ONE)
    public R<SysSetting> getSetting(Integer type) {
        QueryWrapper<SysSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("status", ACTIVE_STATUS);
        wrapper.eq("type", type);
        return R.data(sysSettingService.getOne(wrapper));
    }

    @GetMapping("/list/{type}")
    @LogAnnotation(moduleName = ModuleConstant.SYS_SETTING_MODULE, methodName = MethodConstant.LIST)
    public R<List<SysSetting>> list(@PathVariable Integer type) {
        QueryWrapper<SysSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        return R.data(sysSettingService.list(wrapper));
    }

    /*@DeleteMapping("/{id}")
    @LogAnnotation(moduleName = ModuleConstant.SYS_SETTING_MODULE, methodName = MethodConstant.DELETE)
    public R<Boolean> delete(@PathVariable Integer id) {
        SysSetting sysSetting = sysSettingService.getById(id);
        try {
            sftpUtil.deleteFile(FileConstant.ROOT_PATH + sysSetting.getBgUrl());
        } catch (Exception e) {
            log.error("删除文件{}失败", sysSetting.getBgUrl());
        }
        return R.status(sysSettingService.removeById(id));
    }*/

    @PostMapping("/save")
    @LogAnnotation(moduleName = ModuleConstant.SYS_SETTING_MODULE, methodName = MethodConstant.SAVE)
    public R<Boolean> save(MultipartFile file, @RequestBody SysSetting sysSetting) {
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    String fileType = fileName.substring(fileName.lastIndexOf("."));
                    String path = System.currentTimeMillis() + fileType;
                    sftpUtil.uploadFile(FileConstant.UPLOAD_PATH + path, file.getInputStream());
                    sysSetting.setBgUrl(FileConstant.SYS_SETTING_ROOT_PATH + path);
                    return R.data(sysSettingService.save(sysSetting));
                }
            }
        } catch (Exception e) {
            log.error("系统背景上传保存异常,异常信息：{}", e.getMessage());
        }
        return R.error(ResultEnum.SYSTEM_ERROR);
    }
}
