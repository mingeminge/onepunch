package com.zm.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 14:15
 * ==========================
 **/
@Data
public class RoleVO implements Serializable {

    private static final long serialVersionUID = -2229221086356355229L;

    private Integer id;

    private String name;

    private String description;

    private List<Integer> menus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
