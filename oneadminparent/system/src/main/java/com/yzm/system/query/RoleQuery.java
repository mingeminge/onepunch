package com.yzm.system.query;

import com.yzm.common.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 02:43 2019/12/15
 * ===========================
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleQuery extends BaseQuery {

    private String name;
}
