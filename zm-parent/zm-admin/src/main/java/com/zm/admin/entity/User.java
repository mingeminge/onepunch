package com.zm.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 11:21
 * ==========================
 **/
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 31301005595204171L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String password;

    private String phone;

    private String email;

    private String avatar;

    @TableField(value = "real_name")
    private String realName;

    private Integer sex;

    private String signature;

    private Long deptId;

    private Integer status;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
