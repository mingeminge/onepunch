package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 23:41 2019/12/19
 * ===========================
 */
@Data
public class LoginLog {

    private Long id;

    private String username;

    private String isp;

    private String ip;

    private String address;

    private String system;

    private String browser;

    @TableField("login_time")
    private Date loginTime;

}
