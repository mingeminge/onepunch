package com.yzm.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.common.util.CopyUtil;
import com.yzm.system.controller.vo.MenuMetaVO;
import com.yzm.system.controller.vo.MenuVO;
import com.yzm.system.entity.Menu;
import com.yzm.system.mapper.MenuMapper;
import com.yzm.system.mapper.RoleMenuMapper;
import com.yzm.system.query.MenuQuery;
import com.yzm.system.service.MenuService;
import com.yzm.system.service.dto.MenuDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:29 2019/12/14
 * ===========================
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private MenuMapper menuMapper;

    private RoleMenuMapper roleMenuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    public List<MenuDto> queryList(MenuQuery query) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getName())) {
            wrapper.like("name", query.getName());
        }
        if (null != query.getStatus()) {
            wrapper.eq("status", query.getStatus());
        }
        if (null != query.getType()) {
            wrapper.eq("type", query.getType());
        }
        wrapper.orderByAsc("sort");
        //wrapper.in("status", CoreConstant.ENABLE, CoreConstant.DISABLE);
        List<Menu> menuPage = menuMapper.selectList(wrapper);
        List<MenuDto> menuDtoList = CopyUtil.copyList(menuPage, MenuDto.class);
        return (List<MenuDto>) buildTree(menuDtoList).get("content");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(MenuDto menuDTO) {
        Menu menu = CopyUtil.copyProperties(menuDTO, Menu.class);
        menu.setCreateTime(new Date());
        menu.setHidden(menu.getHidden() == null ? false : menu.getHidden());
        menu.setIFrame(menu.getIFrame() == null ? false : menu.getIFrame());
        return menuMapper.insert(menu);
    }

    /*private static List<MenuVO> buildTree(Serializable id, List<MenuVO> menuVOS) {
        if (!CollectionUtils.isEmpty(menuVOS)) {
            List<MenuVO> menuVOList = new ArrayList<>();
            menuVOS.forEach(permission -> {
                if (permission.getPId().equals(id)) {
                    menuVOList.add(permission);
                }
            });
            for (MenuVO menuVO : menuVOList) {
                menuVO.setChildren(buildTree(menuVO.getId(), menuVOS));
            }
            if (menuVOList.size() == 0) {
                return null;
            }
            return menuVOList;
        }
        return null;
    }*/

    @Override
    public Map<String, Object> buildTree(List<MenuDto> menuDtos) {
        List<MenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDto menuDTO : menuDtos) {
            if (menuDTO.getPid() == 0) {
                trees.add(menuDTO);
            }
            for (MenuDto it : menuDtos) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        Map<String, Object> map = new HashMap<>(2);
        if (trees.size() == 0) {
            trees = menuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        map.put("content", trees);
        map.put("totalElements", menuDtos.size());
        return map;
    }


    @Override
    public Integer delete(MenuDto menuDTO) {
        List<Menu> menus = menuMapper.selectList(null);
        List<MenuDto> menuDtos = CopyUtil.copyList(menus, MenuDto.class);
        List<MenuDto> menuDtos1 = buildDtoTree(menuDTO.getId(), menuDtos);
        deleteChildren(menuDtos1);
        roleMenuMapper.deleteRoleMenuByMenuId(menuDTO.getId());
        return menuMapper.deleteById(menuDTO.getId());
    }


    public void deleteChildren(List<MenuDto> menuDto) {
        if (null != menuDto) {
            for (MenuDto child : menuDto) {
                if (null != child.getPid()) {
                    deleteChildren(child.getChildren());
                }
                menuMapper.deleteById(child.getId());
            }
        }

    }

    private static List<MenuDto> buildDtoTree(Serializable id, List<MenuDto> menuDtos) {
        if (!CollectionUtils.isEmpty(menuDtos)) {
            List<MenuDto> dtoArrayList = new ArrayList<>();
            menuDtos.forEach(menuDto -> {
                if (menuDto.getPid().equals(id)) {
                    dtoArrayList.add(menuDto);
                }
            });
            for (MenuDto menuDto : dtoArrayList) {
                menuDto.setChildren(buildDtoTree(menuDto.getId(), menuDtos));
            }
            if (dtoArrayList.size() == 0) {
                return null;
            }
            return dtoArrayList;
        }
        return null;
    }


    @Override
    public List<MenuVO> buildMenus(List<MenuDto> menuDtos) {
        List<MenuVO> list = new LinkedList<>();
        menuDtos.forEach(menuDTO -> {
                    if (menuDTO != null) {
                        List<MenuDto> menuDtoList = menuDTO.getChildren();
                        MenuVO menuVo = new MenuVO();
                        menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName()) ? menuDTO.getComponentName() : menuDTO.getName());
                        // 一级目录需要加斜杠，不然会报警告
                        menuVo.setPath(menuDTO.getPid() == 0 ? "/" + menuDTO.getPath() : menuDTO.getPath());
                        menuVo.setHidden(menuDTO.getHidden());
                        // 如果不是外链
                        if (!menuDTO.getIFrame()) {
                            if (menuDTO.getPid() == 0) {
                                menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                            } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVO(menuDTO.getName(), menuDTO.getIcon(), !menuDTO.getCache()));
                        if (menuDtoList != null && menuDtoList.size() != 0) {
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDtoList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if (menuDTO.getPid() == 0) {
                            MenuVO menuVo1 = new MenuVO();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if (!menuDTO.getIFrame()) {
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVO> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }
}
