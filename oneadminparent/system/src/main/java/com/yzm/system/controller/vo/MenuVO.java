package com.yzm.system.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 13:05 2019/12/15
 * ===========================
 */
@Data
public class MenuVO {

    /*private Long id;

    private String name;

    private String url;

    private String icon;

    private String identifier;

    private String description;

    private Integer status;

    private Integer type;

    private Integer orderNum;

    private Long pId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private MenuMetaVO meta;*/
    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private MenuMetaVO meta;


    private List<MenuVO> children;
}
