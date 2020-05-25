package com.zm.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.admin.dto.MenuDTO;
import com.zm.admin.dto.RoleDTO;
import com.zm.admin.dto.UserDTO;
import com.zm.admin.entity.*;
import com.zm.admin.mapper.UserMapper;
import com.zm.admin.service.*;
import com.zm.admin.vo.UserVO;
import com.zm.admin.wrapper.MenuWrapper;
import com.zm.admin.wrapper.RoleWrapper;
import com.zm.admin.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

        if (!roleIds.isEmpty()) {
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

            if (!menuIds.isEmpty()) {
                QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
                menuQueryWrapper.in("id", menuIds);
                List<Menu> menuList = menuService.list(menuQueryWrapper);
                List<MenuDTO> menuDTOS = MenuWrapper.build().listDTO(menuList);
                userDTO.setMenus(menuDTOS);
            }
        }

        return userDTO;
    }

    @Override
    public IPage<UserVO> list(Page<UserVO> page, UserVO user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Page<User> pages = new Page<>(page.getCurrent(), page.getSize());
        if (StringUtils.isNotBlank(user.getEmail())) {
            wrapper.like("email", user.getEmail() + "%");
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            wrapper.like("phone", user.getPhone() + "%");
        }
        if (null != user.getSex()) {
            wrapper.eq("sex", user.getSex());
        }
        if (StringUtils.isNotBlank(user.getUsername())) {
            wrapper.like("username", user.getUsername() + "%");
        }
        Page<User> userPage = this.page(pages, wrapper);
        List<UserVO> userVOList = UserWrapper.build().listVO(userPage.getRecords());
        page.setRecords(userVOList);
        page.setTotal(userPage.getTotal());
        page.setPages(userPage.getPages());
        return page;
    }

    @Override
    public UserVO getOne(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return UserWrapper.build().entityVO(baseMapper.selectOne(wrapper));
    }

    @Override
    public Boolean updateByUsername(UserVO userVO) {
        User user = this.getById(userVO.getId());
        return this.save(user);
    }

    @Override
    public Boolean register(UserVO userVO) {
        return null;
    }

    @Override
    public Boolean deleteUser(UserVO userVO) {
        return null;
    }
}
