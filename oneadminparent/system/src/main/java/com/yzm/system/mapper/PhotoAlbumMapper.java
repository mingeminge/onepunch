package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.PhotoAlbum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 15:04 2020/3/26
 * ===========================
 */
@Mapper
@Repository
public interface PhotoAlbumMapper extends BaseMapper<PhotoAlbum> {
}
