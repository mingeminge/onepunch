package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:33 2019/12/15
 * ===========================
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -835608998573137305L;

    @TableId
    private Long id;

    private Integer status;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
