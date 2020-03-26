package com.yzm.system.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 02:49 2019/12/15
 * ===========================
 */
@Data
public class RoleVO {

    private Long id;

    private String name;

    private String description;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<Long> permissions;

    private List<Long> depts;
}
