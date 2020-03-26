package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:25 2019/12/14
 * ===========================
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select menu_id from yc_role_menu where role_id=#{roleId}")
    List<Long> getMenuIds(@Param("roleId") Long roleId);

    @Select({"select rd.dept_id from yc_role_dept rd where rd.role_id=#{roleId} "})
    List<Long> getDeptIds(@Param("roleId") Long roleId);
}
