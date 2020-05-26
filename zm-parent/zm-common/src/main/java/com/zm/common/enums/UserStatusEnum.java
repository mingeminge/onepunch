package com.zm.common.enums;

import lombok.Getter;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 10:49
 * ==========================
 **/
@Getter
public enum UserStatusEnum {
    /**
     *
     */
    ENABLE(0, "正常"),
    DISABLE(1, "禁用"),
    DELETE(2, "删除");
    private final Integer status;

    private final String value;

    UserStatusEnum(Integer status, String value) {
        this.status = status;
        this.value = value;
    }
}
