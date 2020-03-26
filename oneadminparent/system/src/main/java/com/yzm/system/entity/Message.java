package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:05 2020/2/10
 * ===========================
 */
@Data
public class Message {

    @TableId
    private Long id;

    private String value;

    private LocalDateTime time;

    @TableField(exist = false)
    private User user;
}
