package com.zm.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.admin.entity.UserRole;
import com.zm.admin.mapper.UserRoleMapper;
import com.zm.admin.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:59
 * ==========================
 **/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
