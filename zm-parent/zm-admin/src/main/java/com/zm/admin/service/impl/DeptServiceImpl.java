package com.zm.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.admin.entity.Dept;
import com.zm.admin.mapper.DeptMapper;
import com.zm.admin.service.IDeptService;
import org.springframework.stereotype.Service;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:58
 * ==========================
 **/
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {
}
