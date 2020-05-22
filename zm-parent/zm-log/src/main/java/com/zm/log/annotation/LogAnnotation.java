package com.zm.log.annotation;

import java.lang.annotation.*;

/**
 * @author : yizuomin
 * @date : Created in 10:37 2019/3/29
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    String moduleName() default "";

    String methodName() default "";
}
