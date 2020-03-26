package com.yzm.system.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:51 2019/12/14
 * ===========================
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = -5843675636022309283L;

    private Long id;

    private String username;

    private String phone;

    private String email;

    private String realName;

    private Integer sex;

    private Integer status;

    private String avatar;

    private String signature;

    private Long deptId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<Long> roles;
}
