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
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1947256248396104520L;

    private Integer id;

    private String username;

    private String phone;

    private String email;

    private String avatar;

    private String realName;

    private Integer sex;

    private String signature;

    private Long deptId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private List<Integer> roles;
}
