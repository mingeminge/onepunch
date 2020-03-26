package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:45 2019/12/15
 * ===========================
 */
@Mapper
@Repository
public interface DeptMapper extends BaseMapper<Dept> {

    @Select("select name from yc_dept where id=#{pid}")
    String findNameById(@Param("pid") Long id);
}
