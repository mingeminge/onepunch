package com.yzm.loging.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:50 2019/12/19
 * ===========================
 */
@Data
public class SystemLog {

    @TableId
    private Long id;

    private String ip;

    private String username;

    private String uri;

    @TableField("module_name")
    private String moduleName;

    @TableField("method_name")
    private String methodName;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField("execution_time")
    private Long executionTime;

    @TableField("error_flag")
    private Boolean errorFlag;
}
