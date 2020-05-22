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
 * @date 2020/5/22 11:28
 * ==========================
 **/
@Data
@TableName("zm_dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = 3526733724082405627L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String manager;

    private String phone;

    private String email;

    @TableField("parent_id")
    private Long parentId;

    @TableField("order_num")
    private Integer orderNum;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
