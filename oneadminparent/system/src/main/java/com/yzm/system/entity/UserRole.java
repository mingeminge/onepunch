package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 23:07 2019/12/14
 * ===========================
 */
@Data
public class UserRole implements Serializable {

    private static final long serialVersionUID = 8083257606143826939L;

    @TableId
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "role_id")
    private Long roleId;
}
