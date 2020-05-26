package com.zm.common.result;

import com.sun.istack.internal.NotNull;
import com.zm.common.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 10:59
 * ==========================
 **/
@Data
public class R<T> implements Serializable {

    private Integer code;

    private String message;

    private Boolean success;

    private T data;

    public static <T> R<T> data(T data) {
        R<T> r = new R<>();
        r.setCode(ResultEnum.OK.getCode());
        r.setMessage(ResultEnum.OK.getMessage());
        r.setData(data);
        r.setSuccess(Boolean.TRUE);
        return r;
    }

    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.setCode(ResultEnum.OK.getCode());
        r.setMessage(ResultEnum.OK.getMessage());
        r.setSuccess(Boolean.TRUE);
        return r;
    }

    public static <T> R<T> ok(ResultEnum resultEnum) {
        R<T> r = new R<>();
        r.setCode(resultEnum.getCode());
        r.setMessage(resultEnum.getMessage());
        r.setSuccess(Boolean.TRUE);
        return r;
    }

    public static <T> R<T> status(@NotNull Boolean status) {
        R<T> r = new R<>();
        if (status) {
            r.setCode(ResultEnum.OK.getCode());
            r.setMessage(ResultEnum.OK.getMessage());
            r.setSuccess(Boolean.TRUE);
        } else {
            r.setCode(ResultEnum.FAILED.getCode());
            r.setMessage(ResultEnum.FAILED.getMessage());
            r.setSuccess(Boolean.FALSE);
        }
        return r;
    }

    public static <T> R<T> error(ResultEnum resultEnum) {
        R<T> r = new R<>();
        r.setCode(resultEnum.getCode());
        r.setMessage(resultEnum.getMessage());
        r.setSuccess(Boolean.FALSE);
        return r;
    }

    public static <T> R<T> error(Integer code, String message) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        r.setSuccess(Boolean.FALSE);
        return r;
    }

}
