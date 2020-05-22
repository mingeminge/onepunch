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

    private T data;

    public static <T> R<T> data(T data) {
        R<T> resultVO = new R<T>();
        resultVO.setCode(ResultEnum.OK.getCode());
        resultVO.setMessage(ResultEnum.OK.getMessage());
        resultVO.setData(data);
        return resultVO;
    }

    public static <T> R<T> ok() {
        R<T> resultVO = new R<T>();
        resultVO.setCode(ResultEnum.OK.getCode());
        resultVO.setMessage(ResultEnum.OK.getMessage());
        return resultVO;
    }

    public static <T> R<T> ok(ResultEnum resultEnum) {
        R<T> resultVO = new R<T>();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMessage(resultEnum.getMessage());
        return resultVO;
    }

    public static <T> R<T> status(@NotNull Boolean status) {
        R<T> resultVO = new R<T>();
        if (status) {
            resultVO.setCode(ResultEnum.OK.getCode());
            resultVO.setMessage(ResultEnum.OK.getMessage());
        } else {
            resultVO.setCode(ResultEnum.FAILED.getCode());
            resultVO.setMessage(ResultEnum.FAILED.getMessage());
        }
        return resultVO;
    }

    public static <T> R<T> error(ResultEnum resultEnum) {
        R<T> resultVO = new R<T>();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMessage(resultEnum.getMessage());
        return resultVO;
    }

}
