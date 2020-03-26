package com.yzm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.system.entity.LoginSetting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:23 2019/12/27
 * ===========================
 */
@Mapper
@Repository
public interface LoginSettingMapper extends BaseMapper<LoginSetting> {
}
