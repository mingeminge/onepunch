package com.zm.admin.dto;

import com.zm.admin.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 14:02
 * ==========================
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends User {

    private List<RoleDTO> roles;

    private List<MenuDTO> menus;
}
