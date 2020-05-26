package com.zm.admin.controller;

import com.zm.admin.service.IDeptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/26 10:14
 * ==========================
 **/
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
public class DeptController {

    private final IDeptService deptService;
}
