package com.zm.system.config;

import com.zm.common.enums.ResultEnum;
import com.zm.common.exception.ZmException;
import com.zm.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 15:23 2019/12/16
 * ===========================
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(ZmException.class)
    public R exceptionHandle(ZmException e) {
        log.error("异常:{}", e.getMessage());
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R s(Exception e) {
        log.error("异常:{}", e.getMessage());
        return R.error(ResultEnum.SYSTEM_ERROR);
    }

}
