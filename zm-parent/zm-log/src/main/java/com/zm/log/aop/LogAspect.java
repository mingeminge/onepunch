package com.zm.log.aop;


import com.zm.log.annotation.LogAnnotation;
import com.zm.log.entity.SysLog;
import com.zm.log.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author : yizuomin
 * @date : Created in 10:46 2019/3/29
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private long startTime;

    @Autowired
    private ISysLogService systemLogService;

    @Pointcut("@annotation(com.zm.log.annotation.LogAnnotation)")
    public void logPointcut() {

    }

    @Before(value = "logPointcut()")
    public void before() {
        startTime = System.currentTimeMillis();
    }

    @AfterReturning(value = "logPointcut()&&args(object,..)&&@annotation(annotation)")
    public void afterReturn(JoinPoint joinPoint, Object object, LogAnnotation annotation) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            log.info("<===============================================>");
            log.info("用户       :{}", username);
            log.info("模块名称   :{}", annotation.moduleName());
            log.info("方法名称   :{}", annotation.methodName());
            log.info("URL       :{}", request.getRequestURI());
            log.info("METHOD    :{}", request.getMethod());
            log.info("IP_ADDRESS:{}", request.getRemoteAddr());
            log.info("耗时       :{}", (System.currentTimeMillis() - startTime));
            log.info("<===============================================>");
            SysLog systemLog = new SysLog();
            systemLog.setUsername(username);
            systemLog.setModuleName(annotation.moduleName());
            systemLog.setMethodName(annotation.methodName());
            systemLog.setUri(request.getRequestURI());
            systemLog.setIp(request.getRemoteHost());
            systemLog.setExecutionTime(System.currentTimeMillis() - startTime);
            systemLog.setCreateTime(new Date());
            systemLog.setErrorFlag(false);
            systemLogService.save(systemLog);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    @AfterThrowing(value = "logPointcut()&&args(object,..)&&@annotation(annotation)", throwing = "e")
    public void afterReturningMethod(Object object, LogAnnotation annotation, Exception e) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            log.info("<===============================================>");
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("用户       :{}", username);
            log.error("模块名称   :{}", annotation.moduleName());
            log.error("方法名称   :{}", annotation.methodName());
            log.error("URL       :{}", request.getRequestURI());
            log.error("METHOD    :{}", request.getMethod());
            log.error("IP_ADDRESS:{}", request.getRemoteAddr());
            log.error("耗时       :{}", executionTime);
            log.error("是否异常    :{}", true);
            log.error("异常信息     :{}", e.getMessage());
            log.info("<===============================================>");
            SysLog systemLog = new SysLog();
            systemLog.setUsername(username);
            systemLog.setModuleName(annotation.moduleName());
            systemLog.setMethodName(annotation.methodName());
            systemLog.setUri(request.getRequestURI());
            systemLog.setIp(request.getRemoteHost());
            systemLog.setExecutionTime(executionTime);
            systemLog.setCreateTime(new Date());
            systemLog.setErrorFlag(true);
            systemLogService.save(systemLog);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
