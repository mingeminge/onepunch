package com.yzm.loging.query;

import com.yzm.common.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:18 2019/12/19
 * ===========================
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemLogQuery extends BaseQuery {

    private Integer size = 10;

    private Integer current = 1;

    private String username;

    private Boolean errorFlag;

    private String startTime;

    private String endTime;

}
