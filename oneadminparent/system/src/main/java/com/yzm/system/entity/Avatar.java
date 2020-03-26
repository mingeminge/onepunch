package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:11 2019/12/18
 * ===========================
 */
@Data
public class Avatar  {

    @TableId
    private Long id;

    @NotBlank(message = "图片路径不能为空")
    private String imgUrl;

    private String name;

    @TableField("create_time")
    private Date createTime;
}
