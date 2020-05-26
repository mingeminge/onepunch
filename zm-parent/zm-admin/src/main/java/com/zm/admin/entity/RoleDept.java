package com.zm.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 01:36 2019/12/16
 * ===========================
 */
@Data
public class RoleDept {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("role_id")
    private Integer roleId;

    @TableField("dept_id")
    private Integer deptId;
}
