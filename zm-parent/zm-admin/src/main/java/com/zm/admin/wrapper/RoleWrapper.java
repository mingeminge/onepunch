package com.zm.admin.wrapper;

import com.zm.admin.dto.RoleDTO;
import com.zm.admin.entity.Role;
import com.zm.admin.vo.RoleVO;
import com.zm.common.utils.CopyUtil;
import com.zm.common.wrapper.BaseEntityWrapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 14:30
 * ==========================
 **/
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO> {

    public static RoleWrapper build() {
        return new RoleWrapper();
    }

    @Override
    public RoleVO entityVO(Role role) {
        return Objects.requireNonNull(CopyUtil.copyProperties(role, RoleVO.class));
    }

    public RoleDTO entityDTO(Role role) {
        return Objects.requireNonNull(CopyUtil.copyProperties(role, RoleDTO.class));
    }

    public List<RoleDTO> listDTO(List<Role> list) {
        return list.stream().map(this::entityDTO).collect(Collectors.toList());
    }
}
