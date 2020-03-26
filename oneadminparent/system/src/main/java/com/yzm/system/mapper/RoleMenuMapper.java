package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 02:36 2019/12/15
 * ===========================
 */
@Mapper
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    @Delete("delete from yc_role_menu where role_id=#{roleId}")
    Integer deleteRoleMenuByRoleId(@Param("roleId") Long roleId);

    @Delete("delete from yc_role_menu where menu_id=#{menuId}")
    Integer deleteRoleMenuByMenuId(@Param("menuId") Long id);
}
