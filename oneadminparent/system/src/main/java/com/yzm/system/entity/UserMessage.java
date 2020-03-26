package com.yzm.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:15 2020/2/10
 * ===========================
 */
@Data
public class UserMessage {

    @TableField("user_id")
    private Long userId;

    @TableField("message_id")
    private Long messageId;
}
