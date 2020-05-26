package com.zm.admin.controller;

import com.zm.admin.entity.Menu;
import com.zm.admin.service.IMenuService;
import com.zm.admin.vo.MenuVO;
import com.zm.admin.wrapper.MenuWrapper;
import com.zm.common.contant.MethodConstant;
import com.zm.common.contant.ModuleConstant;
import com.zm.common.result.R;
import com.zm.common.utils.CopyUtil;
import com.zm.log.annotation.LogAnnotation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/26 10:13
 * ==========================
 **/
@RestController
@RequestMapping("/menu")
@AllArgsConstructor
public class MenuController {

    private final IMenuService menuService;

    @GetMapping("/list")
    @LogAnnotation(moduleName = ModuleConstant.MENU_MODULE, methodName = MethodConstant.LIST)
    public R<List<MenuVO>> list() {
        List<Menu> list = menuService.list();
        List<MenuVO> menuVOList = MenuWrapper.build().listVO(list);
        return R.data(menuVOList);
    }

    @GetMapping("/{id}")
    @LogAnnotation(moduleName = ModuleConstant.MENU_MODULE, methodName = MethodConstant.GET_ONE)
    public R<MenuVO> findById(@PathVariable Integer id) {
        Menu menu = menuService.getById(id);
        MenuVO menuVO = MenuWrapper.build().entityVO(menu);
        return R.data(menuVO);
    }

    @PostMapping("/save")
    @LogAnnotation(moduleName = ModuleConstant.MENU_MODULE, methodName = MethodConstant.SAVE)
    public R<Boolean> save(@RequestBody MenuVO menuVO) {
        Menu menu = CopyUtil.copyProperties(menuVO, Menu.class);
        return R.status(menuService.save(menu));
    }

    @PostMapping("/update")
    @LogAnnotation(moduleName = ModuleConstant.MENU_MODULE, methodName = MethodConstant.UPDATE)
    public R<Boolean> update(@RequestBody MenuVO menuVO) {
        Menu menu = CopyUtil.copyProperties(menuVO, Menu.class);
        return R.status(menuService.updateById(menu));
    }

    @DeleteMapping("/{id}")
    @LogAnnotation(moduleName = ModuleConstant.MENU_MODULE, methodName = MethodConstant.DELETE)
    public R<Boolean> delete(@PathVariable Integer id) {
        return R.status(menuService.removeById(id));
    }
}
