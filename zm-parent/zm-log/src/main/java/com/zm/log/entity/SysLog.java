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
 * @date 2020/5/22 14:56
 * ==========================
 **/
@Data
@TableName("zm_sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = -7276736834266100624L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String ip;

    private String username;

    private String uri;

    @TableField("module_name")
    private String moduleName;

    @TableField("method_name")
    private String methodName;

    @TableField("create_time")
    private Date createTime;

    @TableField("execution_time")
    private Long executionTime;

    @TableField("error_flag")
    private Boolean errorFlag;

    private String text;
}
