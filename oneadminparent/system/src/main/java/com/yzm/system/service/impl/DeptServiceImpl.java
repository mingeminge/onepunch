package com.yzm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.common.util.CopyUtil;
import com.yzm.system.controller.vo.DeptVO;
import com.yzm.system.entity.Dept;
import com.yzm.system.mapper.DeptMapper;
import com.yzm.system.mapper.RoleDeptMapper;
import com.yzm.system.query.DeptQuery;
import com.yzm.system.service.DeptService;
import com.yzm.system.service.dto.DeptDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:46 2019/12/15
 * ===========================
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    private final DeptMapper deptMapper;

    private final RoleDeptMapper roleDeptMapper;


    @Autowired
    public DeptServiceImpl(DeptMapper deptMapper, RoleDeptMapper roleDeptMapper) {
        this.deptMapper = deptMapper;
        this.roleDeptMapper = roleDeptMapper;
    }

    @Override
    public List<DeptDto> queryList(DeptQuery deptQuery) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(deptQuery.getName())) {
            wrapper.like("name", deptQuery.getName());
        }
        if (null != deptQuery.getStatus()) {
            wrapper.eq("status", deptQuery.getStatus());
        }
        wrapper.orderByAsc("order_num");
        List<Dept> deptEntities = deptMapper.selectList(wrapper);
        List<DeptDto> deptDtos = CopyUtil.copyList(deptEntities, DeptDto.class);
        return deptDtos;
    }

    @Override
    public Integer delete(Long deptId) {
        List<Dept> depts = deptMapper.selectList(null);
        List<DeptDto> deptDtos1 = CopyUtil.copyList(depts, DeptDto.class);
        List<DeptDto> deptDtos = buildDtoTree(deptId, deptDtos1);
        deleteChildren(deptDtos);
        roleDeptMapper.deleteRoleDeptByDeptId(deptId);
        return deptMapper.deleteById(deptId);
    }

    public void deleteChildren(List<DeptDto> deptDto) {
        if (null != deptDto) {
            for (DeptDto child : deptDto) {
                if (null != child.getChildren()) {
                    deleteChildren(child.getChildren());
                }
                deptMapper.deleteById(child.getId());
            }
        }
    }

    private static List<DeptDto> buildDtoTree(Serializable id, List<DeptDto> deptDtos) {
        if (!CollectionUtils.isEmpty(deptDtos)) {
            List<DeptDto> dtoArrayList = new ArrayList<>();
            deptDtos.forEach(deptDto -> {
                if (deptDto.getParentId().equals(id)) {
                    dtoArrayList.add(deptDto);
                }
            });
            for (DeptDto deptDto : dtoArrayList) {
                deptDto.setChildren(buildDtoTree(deptDto.getId(), deptDtos));
            }
            if (dtoArrayList.size() == 0) {
                return null;
            }
            return dtoArrayList;
        }
        return null;
    }
    @Override
    public Object buildTree(List<DeptDto> deptDtos) {
        Set<DeptDto> trees = new LinkedHashSet<>();
        Set<DeptDto> depts= new LinkedHashSet<>();
        List<String> deptNames = deptDtos.stream().map(DeptDto::getName).collect(Collectors.toList());
        boolean isChild;
        for (DeptDto deptDTO : deptDtos) {
            isChild = false;
            if ("0".equals(deptDTO.getParentId().toString())) {
                trees.add(deptDTO);
            }
            for (DeptDto it : deptDtos) {
                if (it.getParentId().equals(deptDTO.getId())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if(isChild) {
                depts.add(deptDTO);
            } else if(!deptNames.contains(deptMapper.findNameById(deptDTO.getParentId()))) {
                depts.add(deptDTO);
            }
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = deptDtos.size();

        Map<String,Object> map = new HashMap<>(2);
        map.put("totalElements",totalElements);
        map.put("content",CollectionUtils.isEmpty(trees)? deptDtos :trees);
        return map;
    }
    private static List<DeptVO> buildTree(Serializable id, List<DeptVO> deptVOList) {
        if (!CollectionUtils.isEmpty(deptVOList)) {
            List<DeptVO> deptVOS = new ArrayList<>();
            deptVOList.forEach(deptVO -> {
                if (deptVO.getParentId().equals(id)) {
                    deptVOS.add(deptVO);
                }
            });
            for (DeptVO deptVO : deptVOS) {
                deptVO.setChildren(buildTree(deptVO.getId(), deptVOList));
            }
            if (deptVOS.size() == 0) {
                return null;
            }
            return deptVOS;
        }
        return deptVOList;
    }
}
