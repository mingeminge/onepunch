package com.yzm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.system.entity.LoginSetting;
import com.yzm.system.mapper.LoginSettingMapper;
import com.yzm.system.service.ILoginSettingService;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:24 2019/12/27
 * ===========================
 */
@Service
public class LoginSettingServiceImpl extends ServiceImpl<LoginSettingMapper, LoginSetting> implements ILoginSettingService {
}
