package com.yzm.system.config;

import com.yzm.common.enums.ResultEnum;
import com.yzm.common.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    public ResultVO exceptionHandle(Exception e) {
        e.printStackTrace();
        return ResultVO.error(ResultEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResultVO s(AccessDeniedException e) {
        e.printStackTrace();
        return ResultVO.error(ResultEnum.PERMISSION_DENIED);
    }

}
