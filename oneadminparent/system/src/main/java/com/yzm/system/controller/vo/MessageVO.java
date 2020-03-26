package com.yzm.system.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:59 2020/2/10
 * ===========================
 */
@Data
public class MessageVO {

    private Long id;

    private String value;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private String username;

    private String avatar;
}
