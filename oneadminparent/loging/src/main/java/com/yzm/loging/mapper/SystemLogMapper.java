package com.yzm.loging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.loging.entity.SystemLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:55 2019/12/19
 * ===========================
 */
@Mapper
@Repository
public interface SystemLogMapper extends BaseMapper<SystemLog> {
}
