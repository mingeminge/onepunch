package com.yzm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzm.system.entity.Menu;
import com.yzm.system.query.MenuQuery;
import com.yzm.system.controller.vo.MenuVO;
import com.yzm.system.service.dto.MenuDto;

import java.util.List;
import java.util.Map;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:28 2019/12/14
 * ===========================
 */
public interface MenuService extends IService<Menu> {

    List<MenuDto> queryList(MenuQuery query);

    Integer insert(MenuDto menuDTO);

    List<MenuVO> buildMenus(List<MenuDto> menuList);

    Map<String, Object> buildTree(List<MenuDto> menuDtos);

    Integer delete(MenuDto menuDTO);
}
