package com.zm.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.admin.entity.Menu;
import com.zm.admin.mapper.MenuMapper;
import com.zm.admin.service.IMenuService;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:57
 * ==========================
 **/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
}
