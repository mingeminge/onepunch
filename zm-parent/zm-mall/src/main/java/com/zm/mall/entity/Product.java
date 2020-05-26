package com.zm.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/25 14:46
 * ==========================
 **/
@Data
@TableName("zm_product")
public class Product {

    @TableId(type = IdType.AUTO)
    private Integer id;
}
