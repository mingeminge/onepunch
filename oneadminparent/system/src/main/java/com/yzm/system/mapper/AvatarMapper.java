package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.Avatar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:14 2019/12/18
 * ===========================
 */
@Mapper
@Repository
public interface AvatarMapper extends BaseMapper<Avatar> {
}
