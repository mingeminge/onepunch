package com.yzm.common.query;

import lombok.Data;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 11:48 2019/12/14
 * ===========================
 */
@Data
public class BaseQuery {

    private Integer size = 10;

    private Integer current = 1;
}
