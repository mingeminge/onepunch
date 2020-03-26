package com.yzm.system.controller.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:13 2019/12/19
 * ===========================
 */
@Data
public class ChangePassword {

    private Integer id;

    private String username;

    @NotBlank
    @Length(min = 6)
    private String oldPassword;

    @NotBlank
    @Length(min = 6)
    private String newPassword;
}
