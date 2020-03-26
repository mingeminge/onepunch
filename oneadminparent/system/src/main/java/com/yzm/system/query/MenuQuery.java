package com.yzm.system.query;

import com.yzm.common.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 15:46 2019/12/15
 * ===========================
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuQuery extends BaseQuery {

    private String name;

    private Integer status;

    private Integer type;
}
