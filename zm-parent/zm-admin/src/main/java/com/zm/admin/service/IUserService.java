package com.zm.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zm.admin.dto.UserDTO;
import com.zm.admin.entity.User;
import com.zm.admin.vo.ResetPasswordVO;
import com.zm.admin.vo.UserVO;

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

    /**
     * 查询用户列表
     *
     * @param user 用户查询条件
     * @param page 分页
     * @return
     */
    IPage<UserVO> list(Page<UserVO> page, UserVO user);

    /**
     * 查询单条
     *
     * @param id 用户id
     * @return
     */
    UserVO findById(Integer id);

    /**
     * 修改
     *
     * @param userVO 用户信息
     * @return
     */
    Boolean updateById(UserVO userVO);

    /**
     * 设置角色
     *
     * @param userVO 用户角色信息
     * @return
     */
    Boolean setRole(UserVO userVO);

    /**
     * 注册用户
     *
     * @param userVO 用户
     * @return
     */
    Boolean register(UserVO userVO);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return
     */
    Boolean deleteUser(Integer id);

    /**
     * 修改密码
     *
     * @param resetPasswordVO 原始密码 新密码
     * @return
     */
    Boolean resetPassword(ResetPasswordVO resetPasswordVO);
}
