package com.zm.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zm.admin.entity.Role;
import com.zm.admin.vo.MenuVO;
import com.zm.admin.vo.RoleVO;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:56
 * ==========================
 **/
public interface IRoleService extends IService<Role> {

    /**
     * 设置权限/菜单
     *
     * @param roleVO 角色菜单信息
     * @return
     */
    Boolean setMenu(RoleVO roleVO);

    /**
     * 删除角色
     *
     * @param roleVO 角色
     * @return
     */
    Boolean delete(RoleVO roleVO);
}
