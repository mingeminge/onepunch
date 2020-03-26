package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:16 2019/12/14
 * ===========================
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7678240730459804159L;

    @TableId
    private Long id;

    private String name;

    private String description;

    @TableField(exist = false)
    private Set<Menu> menus;
}
