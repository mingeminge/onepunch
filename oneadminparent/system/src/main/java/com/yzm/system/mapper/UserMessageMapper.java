package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:16 2020/2/10
 * ===========================
 */
@Mapper
@Repository
public interface UserMessageMapper extends BaseMapper<UserMessage> {

    @Select("select * from yc_user_message where user_id=#{userId}")
    List<UserMessage> usermessageList(@Param("userId") Long userId);
}
