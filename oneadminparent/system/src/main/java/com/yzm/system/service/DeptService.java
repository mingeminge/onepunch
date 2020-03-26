package com.yzm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzm.system.entity.Dept;
import com.yzm.system.query.DeptQuery;
import com.yzm.system.service.dto.DeptDto;

import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:46 2019/12/15
 * ===========================
 */
public interface DeptService extends IService<Dept> {

    List<DeptDto> queryList(DeptQuery deptQuery);

    Integer delete(Long deptId);

    Object buildTree(List<DeptDto> deptDtos);
}
