package com.yzm.common.enums;

import lombok.Getter;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:08 2019/12/16
 * ===========================
 */
@Getter
public enum ResultEnum {
    /**
     *
     */
    PLEASE_LOGIN(0, "请登陆"),
    USERNAME_PASSWORD_ERROR(1, "账号或密码错误"),
    UNKNOWN_ACCOUNT(3, "该用户不存在"),
    DISABLED_ACCOUNT(4, "该用户已被禁用"),
    LOCKED_ACCOUNT(5, "该用户已被锁定"),
    INCORRECT_CREDENTIALS(6, "用户名或密码错误"),
    OK(200, "操作成功"),
    KICK_OUT(7, "您的账号在另一台设备登录。如果不是本人操作，请及时修改密码"),
    PERMISSION_DENIED(403, "抱歉，您没有权限！"),
    SYSTEM_ERROR(500, "出错啦，请联系管理员!"),
    TIME_OUT(10000, "对不起，您的身份已过期，请重新登陆！");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
