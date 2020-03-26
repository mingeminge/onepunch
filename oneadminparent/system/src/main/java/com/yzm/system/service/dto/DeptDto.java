package com.yzm.system.service.dto;

import lombok.Data;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:19 2019/12/26
 * ===========================
 */
@Data
public class DeptDto {

    private Long id;

    private String name;

    private String manager;

    private String phone;

    private String email;

    private Long parentId;

    private Integer orderNum;

    private List<DeptDto> children;
}
