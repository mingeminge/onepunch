package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:25 2019/12/14
 * ===========================
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

}
