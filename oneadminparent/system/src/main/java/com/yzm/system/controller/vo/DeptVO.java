package com.yzm.system.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:53 2019/12/15
 * ===========================
 */
@Data
public class DeptVO {

    private Long id;

    private String name;

    private String manager;

    private String phone;

    private String email;

    private Long parentId;

    private Integer orderNum;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<DeptVO> children;

}
