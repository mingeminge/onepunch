package com.yzm.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzm.common.query.BaseQuery;
import com.yzm.common.util.FileUtil;
import com.yzm.common.vo.PageVO;
import com.yzm.common.vo.ResultVO;
import com.yzm.loging.annotation.LogAnnotation;
import com.yzm.system.entity.Avatar;
import com.yzm.system.entity.PhotoAlbum;
import com.yzm.system.service.impl.PhotoAlbumServiceImpl;
import com.yzm.system.service.impl.PhotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 15:08 2020/3/26
 * ===========================
 */
@RestController
@RequestMapping("/photoAlbum")
public class PhotoAlbumController {

    @Autowired
    private PhotoServiceImpl photoService;

    @Autowired
    private PhotoAlbumServiceImpl photoAlbumService;

    @Autowired
    private FileUtil fileUtil;

    private static final String MODULE_NAME = "相册";

    private static final String UPLOAD_PATH = "/photo/";

    private static final String ROOT_PATH = "/data/sftp/upload";


    @GetMapping("/list")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public PageVO list(BaseQuery query) {
        Page<PhotoAlbum> page = new Page<>(query.getCurrent(), query.getSize());
        Page<PhotoAlbum> avatarPage = photoAlbumService.page(page);
        return PageVO.build(avatarPage.getTotal(), avatarPage.getRecords());
    }

    @PostMapping("/save")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "上传")
    public ResultVO save(@RequestBody PhotoAlbum photoAlbum){
        boolean save = photoAlbumService.save(photoAlbum);
        if(save){
            return ResultVO.ok();
        }
        return ResultVO.error();
    }

    @DeleteMapping("/{id}")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除")
    public ResultVO delete(@PathVariable Long id){
        boolean b = photoAlbumService.removeById(id);
        if(b){
            return ResultVO.ok();
        }
        return ResultVO.error();
    }
}
