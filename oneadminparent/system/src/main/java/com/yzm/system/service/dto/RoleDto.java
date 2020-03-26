package com.yzm.system.service.dto;

import lombok.Data;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 12:16 2019/12/14
 * ===========================
 */
@Data
public class RoleDto {
    private Long id;

    private String name;

    private String description;

    private Date createTime;

    private Date updateTime;
}
