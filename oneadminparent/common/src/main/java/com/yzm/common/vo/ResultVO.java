package com.yzm.common.vo;

import com.yzm.common.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 14:49 2019/12/12
 * ===========================
 */
@Data
@SuppressWarnings("all")
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 4532316201700156434L;

    private Integer code;

    private String message;

    private T data;

    public static <T>ResultVO ok(T data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.OK.getCode());
        resultVO.setMessage(ResultEnum.OK.getMessage());
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO ok() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.OK.getCode());
        resultVO.setMessage(ResultEnum.OK.getMessage());
        return resultVO;
    }

    public static ResultVO error() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SYSTEM_ERROR.getCode());
        resultVO.setMessage(ResultEnum.SYSTEM_ERROR.getMessage());
        return resultVO;
    }

    public static ResultVO error(String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SYSTEM_ERROR.getCode());
        resultVO.setMessage(message);
        return resultVO;
    }

    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMessage(resultEnum.getMessage());
        return resultVO;
    }

}
