package com.yzm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.system.entity.Photo;
import com.yzm.system.mapper.PhotoMapper;
import com.yzm.system.service.PhotoService;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 15:07 2020/3/26
 * ===========================
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {
}
