package com.yzm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.common.constant.CoreConstant;
import com.yzm.common.util.CopyUtil;
import com.yzm.common.vo.PageVO;
import com.yzm.system.entity.Role;
import com.yzm.system.entity.RoleDept;
import com.yzm.system.entity.RoleMenu;
import com.yzm.system.controller.vo.RoleVO;
import com.yzm.system.mapper.RoleDeptMapper;
import com.yzm.system.mapper.RoleMapper;
import com.yzm.system.mapper.RoleMenuMapper;
import com.yzm.system.query.RoleQuery;
import com.yzm.system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:28 2019/12/14
 * ===========================
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private RoleMapper roleMapper;

    private RoleMenuMapper roleMenuMapper;

    private RoleDeptMapper roleDeptMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, RoleMenuMapper roleMenuMapper, RoleDeptMapper roleDeptMapper) {
        this.roleMapper = roleMapper;
        this.roleMenuMapper = roleMenuMapper;
        this.roleDeptMapper = roleDeptMapper;
    }

    @Override
    public PageVO<Role> queryList(RoleQuery query) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getName())) {
            wrapper.like("name", query.getName());
        }
        wrapper.in("status", CoreConstant.ENABLE, CoreConstant.DISABLE);
        Page<Role> page = new Page<>(query.getCurrent(), query.getSize());
        Page<Role> rolePage = roleMapper.selectPage(page, wrapper);
        List<RoleVO> roleVOS = CopyUtil.copyList(rolePage.getRecords(), RoleVO.class);
        return PageVO.build(rolePage.getTotal(), roleVOS);
    }

    @Override
    public RoleVO findById(Long id) {
        Role role = roleMapper.selectById(id);
        RoleVO roleVO = CopyUtil.copyProperties(role, RoleVO.class);
        roleVO.setPermissions(roleMapper.getMenuIds(id));
        roleVO.setDepts(roleMapper.getDeptIds(id));
        return roleVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer edit(RoleVO roleVO) {
        Role role = CopyUtil.copyProperties(roleVO, Role.class);
        roleMenuMapper.deleteRoleMenuByRoleId(roleVO.getId());
        roleDeptMapper.deleteRoleDeptByRoleId(roleVO.getId());
        this.insetRolePermission(roleVO);
        this.insertRoleDept(roleVO);
        role.setUpdateTime(new Date());
        return roleMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(RoleVO roleVO) {
        Role role = CopyUtil.copyProperties(roleVO, Role.class);
        role.setCreateTime(new Date());
        int insert = roleMapper.insert(role);
        roleVO.setId(role.getId());
        this.insetRolePermission(roleVO);
        this.insertRoleDept(roleVO);
        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(RoleVO roleVO) {
        roleMenuMapper.deleteRoleMenuByRoleId(roleVO.getId());
        roleDeptMapper.deleteRoleDeptByRoleId(roleVO.getId());
        return roleMapper.deleteById(roleVO.getId());
    }

    private void insetRolePermission(RoleVO roleVO) {
        if (!CollectionUtils.isEmpty(roleVO.getPermissions())) {
            for (Long permissionId : roleVO.getPermissions()) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(permissionId);
                roleMenu.setRoleId(roleVO.getId());
                roleMenuMapper.insert(roleMenu);
            }
        }
    }

    private void insertRoleDept(RoleVO roleVO) {
        if (!CollectionUtils.isEmpty(roleVO.getDepts())) {
            for (Long deptId : roleVO.getDepts()) {
                RoleDept roleDept = new RoleDept();
                roleDept.setDeptId(deptId);
                roleDept.setRoleId(roleVO.getId());
                roleDeptMapper.insert(roleDept);
            }
        }
    }
}
