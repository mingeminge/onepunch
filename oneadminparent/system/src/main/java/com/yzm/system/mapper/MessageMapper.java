package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:10 2020/2/10
 * ===========================
 */
@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    @Select("SET NAMES 'utf8mb4'")
    void setUTF8MB4();
}
