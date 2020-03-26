package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 23:06 2019/12/14
 * ===========================
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Delete("delete from yc_user_role where user_id=#{userId}")
    Integer deleteUserRole(@Param("userId") Long userId);

}
