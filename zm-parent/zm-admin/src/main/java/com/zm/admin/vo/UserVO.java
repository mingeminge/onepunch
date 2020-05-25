package com.zm.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 14:15
 * ==========================
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVO {

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
}
