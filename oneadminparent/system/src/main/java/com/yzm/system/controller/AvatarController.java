package com.yzm.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzm.common.enums.ResultEnum;
import com.yzm.common.query.BaseQuery;
import com.yzm.common.vo.PageVO;
import com.yzm.common.vo.ResultVO;
import com.yzm.loging.annotation.LogAnnotation;
import com.yzm.system.entity.Avatar;
import com.yzm.system.service.AvatarService;
import com.yzm.common.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:17 2019/12/18
 * ===========================
 */
@RestController
@RequestMapping("/avatar")
@Slf4j
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private FileUtil fileUtil;

    private static final String MODULE_NAME = "头像管理";

    private static final String UPLOAD_PATH = "/image/";

    private static final String ROOT_PATH = "/data/sftp/upload";

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:avatar:get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public PageVO list(BaseQuery query) {
        Page<Avatar> page = new Page<>(query.getCurrent(), query.getSize());
        Page<Avatar> avatarPage = avatarService.page(page);
        return PageVO.build(avatarPage.getTotal(), avatarPage.getRecords());
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('system:avatar:edit')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改")
    public ResultVO update(@RequestBody Avatar Avatar) {

        boolean update = avatarService.updateById(Avatar);
        if (update) {
            return ResultVO.ok();
        }
        return ResultVO.error(ResultEnum.SYSTEM_ERROR);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('system:avatar:delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除")
    public ResultVO delete(@PathVariable Long id) {
        Avatar avatar = avatarService.getById(id);
        try {
            fileUtil.deleteFile(ROOT_PATH + UPLOAD_PATH + avatar.getImgUrl());
            boolean delete = avatarService.removeById(id);
            if (delete) {
                return ResultVO.ok();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除文件{}失败", avatar.getImgUrl());
        }
        return ResultVO.error(ResultEnum.SYSTEM_ERROR);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('system:avatar:insert')")
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
                return ResultVO.ok(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(ResultEnum.SYSTEM_ERROR);
        }
        return ResultVO.error(ResultEnum.SYSTEM_ERROR);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:avatar:insert')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "上传")
    public ResultVO save(@Validated @RequestBody Avatar avatar, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVO.error(result.getFieldError().getDefaultMessage());
        }
        boolean save = avatarService.save(avatar);
        if (save) {
            return ResultVO.ok();
        }
        return ResultVO.error(ResultEnum.SYSTEM_ERROR);
    }

}
