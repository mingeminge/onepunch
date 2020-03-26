package com.yzm.system.controller.vo;

import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 16:56 2019/12/16
 * ===========================
 */
@Data
public class LoginUser {

    private String username;

    private String password;

    private String system;

    private String browser;

    private Boolean rememberMe;

}
