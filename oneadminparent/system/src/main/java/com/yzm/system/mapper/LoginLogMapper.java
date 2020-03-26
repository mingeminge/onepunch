package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 23:43 2019/12/19
 * ===========================
 */
@Mapper
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    @Select("select * from yc_login_log where login_time=(SELECT max(login_time)FROM yc_login_log where username=#{username})")
    LoginLog getLastLogin(@Param("username") String username);
}
