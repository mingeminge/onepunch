package com.zm.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 15:04
 * ==========================
 **/
@Data
@TableName("zm_sys_login_log")
public class SysLoginLog implements Serializable {

    private static final long serialVersionUID = 7341381238053002056L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String ipAddress;

    @TableField("system_name")
    private String systemName;

    private String browser;

    private String country;

    private String province;

    private String city;

    private String operators;

    @TableField("login_date")
    private Date loginDate;
}
