package com.zm.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.admin.entity.Role;
import com.zm.admin.entity.RoleMenu;
import com.zm.admin.mapper.RoleMapper;
import com.zm.admin.service.IRoleMenuService;
import com.zm.admin.service.IRoleService;
import com.zm.admin.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:56
 * ==========================
 **/
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final IRoleMenuService roleMenuService;

    @Override
    public Boolean setMenu(RoleVO roleVO) {
        QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.eq("role_id", roleVO.getId());
        roleMenuService.remove(roleMenuWrapper);
        if (CollectionUtils.isNotEmpty(roleVO.getMenus())) {
            List<RoleMenu> roleMenuList = new ArrayList<>();
            roleVO.getMenus().forEach(menuId -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleVO.getId());
                roleMenuList.add(roleMenu);
            });
            return roleMenuService.saveBatch(roleMenuList);
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean delete(RoleVO roleVO) {
        QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.eq("role_id", roleVO.getId());
        roleMenuService.remove(roleMenuWrapper);
        return this.removeById(roleVO.getId());
    }
}
