package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 02:37 2019/12/15
 * ===========================
 */
@Data
public class RoleMenu {

    @TableField("role_id")
    private Long roleId;

    @TableField("menu_id")
    private Long menuId;
}
