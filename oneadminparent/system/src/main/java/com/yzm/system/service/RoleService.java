package com.yzm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzm.system.entity.Role;
import com.yzm.system.query.RoleQuery;
import com.yzm.common.vo.PageVO;
import com.yzm.system.controller.vo.RoleVO;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:27 2019/12/14
 * ===========================
 */
public interface RoleService extends IService<Role> {

    PageVO<Role> queryList(RoleQuery query);

    RoleVO findById(Long id);

    Integer edit(RoleVO roleVO);

    Integer insert(RoleVO roleVO);

    Integer delete(RoleVO roleVO);
}
