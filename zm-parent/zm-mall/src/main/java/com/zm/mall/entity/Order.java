package com.zm.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/25 14:50
 * ==========================
 **/
@Data
@TableName("zm_order")
public class Order {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("buyer_company_name")
    private String buyerCompanyName;

    private Integer status;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
