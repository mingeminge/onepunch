package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:24 2019/12/14
 * ===========================
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select({"select ur.role_id from yc_user_role ur where ur.user_id=#{userId} "})
    List<Long> getRoleIds(@Param("userId") Long userId);

    User findByUsername(@Param("username") String username);

    @Select("select account as username,avatar,phone,email,real_name as realName ,sex from yc_user where id=#{userId}")
    User findByUserId(@Param("userId")Long userId);
}
