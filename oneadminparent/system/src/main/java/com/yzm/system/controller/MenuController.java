package com.yzm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzm.common.util.CopyUtil;
import com.yzm.common.vo.ResultVO;
import com.yzm.loging.annotation.LogAnnotation;
import com.yzm.system.entity.Menu;
import com.yzm.system.controller.vo.MenuVO;
import com.yzm.system.query.MenuQuery;
import com.yzm.system.service.MenuService;
import com.yzm.system.service.dto.MenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 12:57 2019/12/15
 * ===========================
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    private MenuService menuService;

    private static final String MODULE_NAME = "权限管理";

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:menu:get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public ResultVO<MenuDto> list(MenuQuery query) {
        List<MenuDto> menuDtoList = menuService.queryList(query);
        return ResultVO.ok(menuDtoList);
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('system:menu:insert')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "新增")
    public ResultVO insert(@RequestBody MenuDto menuDTO) {
        Integer insert = menuService.insert(menuDTO);
        if (null == insert) {
            return ResultVO.error();
        }
        return ResultVO.ok();
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('system:menu:edit')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "修改")
    public ResultVO edit(@RequestBody MenuDto menuDTO) {
        Menu menu = CopyUtil.copyProperties(menuDTO, Menu.class);
        menu.setUpdateTime(new Date());
        boolean update = menuService.updateById(menu);
        if (update) {
            return ResultVO.ok();
        }
        return ResultVO.error();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:menu:get')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "查看")
    public ResultVO findById(@PathVariable Long id) {
        Menu byId = menuService.getById(id);
        MenuDto menuDTO = CopyUtil.copyProperties(byId, MenuDto.class);
        return ResultVO.ok(menuDTO);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:menu:delete')")
    @LogAnnotation(moduleName = MODULE_NAME, methodName = "删除")
    public ResultVO delete(@RequestBody MenuDto menuDTO) {
        Integer update = menuService.delete(menuDTO);
        if (null != update) {
            return ResultVO.ok();
        }
        return ResultVO.error();
    }

    @GetMapping("/buildMenus")
    public ResultVO buildMenus() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.in("type", 0, 1);
        List<Menu> list = menuService.list(wrapper);
        List<MenuDto> menuDtos = CopyUtil.copyList(list, MenuDto.class);
        List<MenuDto> menuDtoList = (List<MenuDto>) menuService.buildTree(menuDtos).get("content");
        List<MenuVO> menuVOS = menuService.buildMenus(menuDtoList);
        return ResultVO.ok(menuVOS);
    }
}
