package com.zm.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.admin.dto.RoleDTO;
import com.zm.admin.dto.UserDTO;
import com.zm.admin.entity.*;
import com.zm.admin.mapper.UserMapper;
import com.zm.admin.service.*;
import com.zm.admin.vo.ResetPasswordVO;
import com.zm.admin.vo.UserVO;
import com.zm.admin.wrapper.MenuWrapper;
import com.zm.admin.wrapper.RoleWrapper;
import com.zm.admin.wrapper.UserWrapper;
import com.zm.common.contant.UserConstant;
import com.zm.common.enums.ResultEnum;
import com.zm.common.enums.UserStatusEnum;
import com.zm.common.exception.ZmException;
import com.zm.common.utils.CopyUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
@Transactional(rollbackFor = Exception.class)
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
            throw new ZmException(ResultEnum.USER_NOT_FOUND);
        }
        UserDTO userDTO = UserWrapper.build().entityDTO(user);

        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", user.getId());
        List<UserRole> userRoleList = userRoleService.list(userRoleQueryWrapper);

        List<Integer> roleIds = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            roleIds.add(userRole.getRoleId());
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
                userDTO.setMenus(MenuWrapper.build().listDTO(menuList));
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
    public UserVO findById(Integer id) {
        return UserWrapper.build().entityVO(this.getById(id));
    }

    @Override
    public Boolean updateById(UserVO userVO) {
        User user = this.getById(userVO.getId());
        if (null == user) {
            throw new ZmException(ResultEnum.USER_NOT_FOUND);
        }
        BeanUtils.copyProperties(userVO, user);
        user.setUpdateTime(LocalDateTime.now());
        return this.updateById(user);
    }

    @Override
    public Boolean setRole(UserVO userVO) {
        QueryWrapper<UserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.eq("user_id", userVO.getId());
        userRoleService.remove(userRoleWrapper);
        if (CollectionUtils.isNotEmpty(userVO.getRoles())) {
            List<UserRole> userRoles = new ArrayList<>();
            userVO.getRoles().forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userVO.getId());
                userRoles.add(userRole);
            });
            return userRoleService.saveBatch(userRoles);
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean register(UserVO userVO) {
        QueryWrapper<User> usernameWrapper = new QueryWrapper<>();
        usernameWrapper.eq("username", userVO.getUsername());
        User user = baseMapper.selectOne(usernameWrapper);
        if (null != user) {
            throw new ZmException(ResultEnum.USER_NAME_IS_REGISTER);
        }
        QueryWrapper<User> phoneWrapper = new QueryWrapper<>();
        phoneWrapper.eq("phone", userVO.getPhone());
        User byPhone = baseMapper.selectOne(phoneWrapper);
        if (null != byPhone) {
            throw new ZmException(ResultEnum.PHONE_IS_REGISTER);
        }
        User registerUser = CopyUtil.copyProperties(userVO, User.class);
        registerUser.setPassword(new BCryptPasswordEncoder().encode(UserConstant.DEFAULT_PASSWORD));
        registerUser.setAvatar("");
        registerUser.setRealName("");
        registerUser.setSignature("");
        registerUser.setCreateTime(LocalDateTime.now());
        return this.save(registerUser);
    }

    @Override
    public Boolean deleteUser(Integer id) {
        User user = this.getById(id);
        user.setStatus(UserStatusEnum.DELETE.getStatus());
        return this.updateById(user);
    }

    @Override
    public Boolean resetPassword(ResetPasswordVO resetPasswordVO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = this.getById(resetPasswordVO.getUserId());
        if (!passwordEncoder.matches(user.getPassword(), resetPasswordVO.getOldPassword())) {
            throw new ZmException(ResultEnum.OLD_PASSWORD_ERROR);
        }
        String password = passwordEncoder.encode(resetPasswordVO.getNewPassword());
        user.setPassword(password);
        return this.updateById(user);
    }
}
