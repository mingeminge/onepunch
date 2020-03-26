package com.yzm.system.service.dto;

import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 13:44 2019/12/20
 * ===========================
 */
@Data
public class ChartDto {

    private Long count;

    private Long user;

    private String time;

    private String name;

    private Long value;
}
