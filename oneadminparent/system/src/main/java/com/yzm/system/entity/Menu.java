package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:19 2019/12/14
 * ===========================
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6823582519593424817L;

    @TableId
    private Long id;

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

   /* private String description;

    @TableField(value = "order_num")
    private Integer orderNum;*/


}
