package com.zm.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/26 10:58
 * ==========================
 **/
@Data
public class SysSetting implements Serializable {

    private static final long serialVersionUID = 9194423857193719976L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer type;

    @TableField("bg_url")
    private String bgUrl;

    @TableField("jump_time")
    private Long jumpTime;

    private Boolean status;

    @TableField("create_time")
    private LocalDateTime createTime;
}
