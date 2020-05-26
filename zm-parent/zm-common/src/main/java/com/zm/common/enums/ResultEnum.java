package com.zm.common.enums;

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
    USER_NOT_FOUND(3, "该用户不存在"),
    DISABLED_ACCOUNT(4, "该用户已被禁用"),
    USER_NAME_IS_REGISTER(5, "该用户名已注册"),
    INCORRECT_CREDENTIALS(6, "用户名或密码错误"),
    PHONE_IS_REGISTER(7, "该手机号已注册"),
    OLD_PASSWORD_ERROR(8, "原始密码错误"),
    OK(200, "操作成功"),
    FAILED(400, "操作失败"),
    PERMISSION_DENIED(403, "抱歉，您没有权限！"),
    SYSTEM_ERROR(500, "出错啦，请联系管理员!"),
    TIME_OUT(10000, "对不起，您的身份已过期，请重新登陆！");

    private final Integer code;

    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
