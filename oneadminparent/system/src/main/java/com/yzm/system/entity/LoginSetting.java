package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:21 2019/12/27
 * ===========================
 */
@Data
@EqualsAndHashCode
public class LoginSetting {

    @TableId
    private Long id;

    private String url;

    @NotNull
    private Integer type;

    private Long time;

    @TableField("is_on")
    private Boolean isOn;

    @TableField("create_time")
    private LocalDateTime createTime;
}
