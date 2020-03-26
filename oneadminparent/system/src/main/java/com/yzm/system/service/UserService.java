package com.yzm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzm.system.entity.User;
import com.yzm.system.query.UserQuery;
import com.yzm.common.vo.PageVO;
import com.yzm.system.controller.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:26 2019/12/14
 * ===========================
 */
public interface UserService extends IService<User>, UserDetailsService {

    PageVO<User> queryList(UserQuery query);

    UserVO findById(Long id);

    Integer edit(UserVO userVO);

    Integer insert(UserVO userVO);

    Integer delete(UserVO userVO);

    User findByUsername(String username);
}
