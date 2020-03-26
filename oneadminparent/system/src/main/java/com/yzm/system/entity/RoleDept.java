package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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

    @TableField("role_id")
    private Long roleId;

    @TableField("dept_id")
    private Long deptId;
}
