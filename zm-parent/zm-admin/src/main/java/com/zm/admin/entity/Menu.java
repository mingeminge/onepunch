package com.zm.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 11:26
 * ==========================
 **/
@Data
@TableName("zm_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = -851023412003955452L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String path;

    private String component;

    private Integer type;

    private String permission;

    @TableField("component_name")
    private String componentName;

    private String icon;

    private Boolean cache;

    private Boolean hidden;

    private Long sort;

    @TableField(value = "pid")
    private Long pid;

    @TableField(value = "i_frame")
    private Boolean iFrame;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
