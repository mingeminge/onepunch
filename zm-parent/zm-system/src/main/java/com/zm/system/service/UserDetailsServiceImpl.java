package com.zm.system.service;

import com.zm.admin.dto.UserDTO;
import com.zm.admin.service.IUserService;
import com.zm.common.enums.ResultEnum;
import com.zm.common.utils.CopyUtil;
import com.zm.system.pojo.SystemUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:51
 * ==========================
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    @Autowired
    public UserDetailsServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public SystemUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.findByUsername(username);
        if (null == userDTO) {
            throw new UsernameNotFoundException(ResultEnum.UNKNOWN_ACCOUNT.getMessage());
        }
        SystemUser systemUser = CopyUtil.copyProperties(userDTO, SystemUser.class);
        if (null != userDTO.getRoles()) {
            List<String> roles = new ArrayList<>();
            userDTO.getRoles().forEach(roleDTO -> {
                roles.add(roleDTO.getName());
            });
            systemUser.setRoles(roles);
        }
        if (null != userDTO.getMenus()) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            List<String> permissions = new ArrayList<>();
            userDTO.getMenus().forEach(menuDTO -> {
                if (StringUtils.isNotBlank(menuDTO.getPermission())) {
                    authorities.add(new SimpleGrantedAuthority(menuDTO.getPermission()));
                    permissions.add(menuDTO.getPermission());
                }
            });
            systemUser.setAuthorities(authorities);
            systemUser.setPermissions(permissions);
        }
        return systemUser;
    }
}
