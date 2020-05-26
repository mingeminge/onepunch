package com.zm.common.exception;

import com.zm.common.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:55
 * ==========================
 **/
@Setter
@Getter
public class ZmException extends RuntimeException {

    private Integer code;

    private String message;

    public ZmException(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }
}
