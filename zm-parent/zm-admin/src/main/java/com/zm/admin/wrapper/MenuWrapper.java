package com.zm.admin.wrapper;

import com.zm.admin.dto.MenuDTO;
import com.zm.admin.entity.Menu;
import com.zm.admin.vo.MenuVO;
import com.zm.common.utils.CopyUtil;
import com.zm.common.wrapper.BaseEntityWrapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 14:31
 * ==========================
 **/
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {
    public static MenuWrapper build() {
        return new MenuWrapper();
    }

    @Override
    public MenuVO entityVO(Menu menu) {
        return Objects.requireNonNull(CopyUtil.copyProperties(menu, MenuVO.class));
    }

    public MenuDTO entityDTO(Menu menu) {
        return Objects.requireNonNull(CopyUtil.copyProperties(menu, MenuDTO.class));
    }

    public List<MenuDTO> listDTO(List<Menu> list) {
        return list.stream().map(this::entityDTO).collect(Collectors.toList());
    }
}
