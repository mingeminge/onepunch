package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 15:00 2020/3/26
 * ===========================
 */
@Data
public class PhotoAlbum {

    @TableId
    private Long id;

    private String name;

    @TableField("photo_num")
    private Integer photoNum;

    @TableField("create_time")
    private Date createTime;

    @TableField("default_img")
    private String defaultImg;

    private Long userId;
}
