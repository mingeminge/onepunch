package com.yzm.system.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 16:59 2019/4/19
 * ===========================
 */
@Data
public class UserInfo {

    private String token;

    private String username;

    private String nickName;

    private String imgUrl;

    /**
     * 登陆地点
     */
    private String address;

    /**
     * 网络类型 电信移动联通
     */
    private String isp;

    private String ip;

    /**
     * 操作系统
     */
    private String system;

    private String avatar;

    private String signature;

    /**
     * 浏览器
     */
    private String browser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    private List<String> roles;

    private List<String> authorities;
}
