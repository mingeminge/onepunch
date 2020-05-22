package com.zm.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.admin.entity.Role;
import com.zm.admin.mapper.RoleMapper;
import com.zm.admin.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:56
 * ==========================
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
}
