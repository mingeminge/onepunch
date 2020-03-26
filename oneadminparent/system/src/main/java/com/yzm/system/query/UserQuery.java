package com.yzm.system.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzm.common.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 11:47 2019/12/14
 * ===========================
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQuery<T> extends BaseQuery {


    private String account;

    private String phone;

    private String email;

    private Integer sex;

    private Long deptId;

    public Page<T> getPage() {
        Page<T> pagePlus = new Page<T>();
        pagePlus.setCurrent(super.getCurrent());
        pagePlus.setSize(super.getSize());
        return pagePlus;
    }
}
