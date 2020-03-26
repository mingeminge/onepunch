package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.RoleDept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 01:37 2019/12/16
 * ===========================
 */
@Mapper
@Repository
public interface RoleDeptMapper extends BaseMapper<RoleDept> {

    @Delete("delete from yc_role_dept where role_id=#{roleId}")
    Integer deleteRoleDeptByRoleId(@Param("roleId") Long roleId);

    @Delete("delete from yc_role_dept where dept_id=#{deptId}")
    void deleteRoleDeptByDeptId(@Param("deptId")Long deptId);
}
