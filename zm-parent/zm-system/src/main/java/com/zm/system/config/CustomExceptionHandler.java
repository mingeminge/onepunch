package com.zm.system.config;

import com.zm.common.enums.ResultEnum;
import com.zm.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(Exception.class)
    public R exceptionHandle(Exception e) {
        e.printStackTrace();
        return R.error(ResultEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R s(AccessDeniedException e) {
        e.printStackTrace();
        return R.error(ResultEnum.PERMISSION_DENIED);
    }

}
