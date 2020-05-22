package com.zm.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 02:37 2019/12/15
 * ===========================
 */
@Data
@TableName("zm_role_menu")
public class RoleMenu {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("role_id")
    private Integer roleId;

    @TableField("menu_id")
    private Integer menuId;
}
