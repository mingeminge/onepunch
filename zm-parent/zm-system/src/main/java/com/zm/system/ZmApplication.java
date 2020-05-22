package com.zm.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 16:36
 * ==========================
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.zm"})
@MapperScan("com.zm.*.mapper")
public class ZmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZmApplication.class, args);
    }
}
