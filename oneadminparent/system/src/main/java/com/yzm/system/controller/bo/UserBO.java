package com.yzm.system.controller.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 12:15 2019/12/14
 * ===========================
 */
@Data
public class UserBO {

    @ExcelProperty(value = "ID", index = 0)
    private Long id;

    @ExcelProperty(value = "用户名", index = 1)
    private String username;

    @ExcelProperty(value = "手机号码", index = 2)
    private String phone;

    @ExcelProperty(value = "邮箱", index = 3)
    private String email;

    @ExcelProperty(value = "真实姓名", index = 4)
    private String realName;

    @ExcelProperty(value = "性别", index = 5)
    private Integer sex;

    @ExcelProperty(value = "状态", index = 6)
    private Boolean status;

    @ExcelProperty(value = "创建时间", index = 7)
    private Date createTime;

    @ExcelProperty(value = "修改时间", index = 8)
    private Date updateTime;
}
