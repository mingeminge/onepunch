package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:40 2019/12/15
 * ===========================
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Dept extends BaseEntity {

    private String name;

    private String manager;

    private String phone;

    private String email;

    @TableField("parent_id")
    private Long parentId;

    @TableField("order_num")
    private Integer orderNum;
}
