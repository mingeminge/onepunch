package com.zm.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zm.admin.dto.UserDTO;
import com.zm.admin.entity.User;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 13:48
 * ==========================
 **/
public interface IUserService extends IService<User> {

    /**
     * 通过用户查询
     *
     * @param username 用户名
     * @return
     */
    UserDTO findByUsername(String username);
}
