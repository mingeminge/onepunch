package com.zm.system.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/25 10:29
 * ==========================
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 8497257519415027244L;

    private Integer id;

    private String username;

    private String token;

    private String system;

    private String browser;

    private String address;

    private String isp;

    private String ip;

    private List<String> roles;

    private List<String> permissions;
}
