package com.yzm.system.service.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 12:15 2019/12/14
 * ===========================
 */
@Data
public class UserDto {

    private Long id;

    private String username;

    private String phone;

    private String email;

    private String realName;

    private Integer sex;

    private Boolean status;

    private Date createTime;

    private Date updateTime;

    private List<RoleDto> roles;
}
