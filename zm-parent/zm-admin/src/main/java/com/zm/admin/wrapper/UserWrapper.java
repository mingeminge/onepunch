package com.zm.admin.wrapper;

import com.zm.admin.dto.UserDTO;
import com.zm.admin.entity.User;
import com.zm.admin.vo.UserVO;
import com.zm.common.utils.CopyUtil;
import com.zm.common.wrapper.BaseEntityWrapper;

import java.util.Objects;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 14:06
 * ==========================
 **/
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

    public static UserWrapper build() {
        return new UserWrapper();
    }

    @Override
    public UserVO entityVO(User user) {
        return Objects.requireNonNull(CopyUtil.copyProperties(user, UserVO.class));
    }

    public UserDTO entityDTO(User user) {
        return Objects.requireNonNull(CopyUtil.copyProperties(user, UserDTO.class));
    }
}
