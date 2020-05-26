package com.zm.admin.vo;

import lombok.Data;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/25 15:35
 * ==========================
 **/
@Data
public class ResetPasswordVO {

    private Integer userId;

    private String oldPassword;

    private String newPassword;
}
