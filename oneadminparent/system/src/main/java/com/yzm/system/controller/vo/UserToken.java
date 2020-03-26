package com.yzm.system.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 00:59 2019/12/20
 * ===========================
 */
@Data
public class UserToken {

    private String token;

    private String username;

    private List<String> roles;

    private List<String> authorities;
}
