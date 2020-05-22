package com.zm.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.admin.dto.MenuDTO;
import com.zm.admin.dto.RoleDTO;
import com.zm.admin.dto.UserDTO;
import com.zm.admin.entity.*;
import com.zm.admin.mapper.UserMapper;
import com.zm.admin.service.*;
import com.zm.admin.wrapper.MenuWrapper;
import com.zm.admin.wrapper.RoleWrapper;
import com.zm.admin.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:48
 * ==========================
 **/
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final IRoleService roleService;
    private final IUserRoleService userRoleService;
    private final IMenuService menuService;
    private final IRoleMenuService roleMenuService;


    @Override
    public UserDTO findByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);
        if (null == user) {
            return null;
        }
        UserDTO userDTO = UserWrapper.build().entityDTO(user);

        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", user.getId());
        List<UserRole> userRoleList = userRoleService.list(userRoleQueryWrapper);

        List<Integer> roleIds = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            roleIds.add(userRole.getId());
        }

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.in("id", roleIds);
        List<Role> roleList = roleService.list(roleQueryWrapper);
        List<RoleDTO> roles = RoleWrapper.build().listDTO(roleList);
        userDTO.setRoles(roles);

        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.in("role_id", roleIds);
        List<RoleMenu> roleMenuList = roleMenuService.list(roleMenuQueryWrapper);

        List<Integer> menuIds = new ArrayList<>();
        for (RoleMenu roleMenu : roleMenuList) {
            menuIds.add(roleMenu.getMenuId());
        }

        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.in("id", menuIds);
        List<Menu> menuList = menuService.list(menuQueryWrapper);
        List<MenuDTO> menuDTOS = MenuWrapper.build().listDTO(menuList);
        userDTO.setMenus(menuDTOS);
        return userDTO;
    }
}
