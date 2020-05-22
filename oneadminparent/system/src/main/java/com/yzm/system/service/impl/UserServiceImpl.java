package com.yzm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.common.constant.CoreConstant;
import com.yzm.common.enums.ResultEnum;
import com.yzm.common.util.CopyUtil;
import com.yzm.common.vo.PageVO;
import com.yzm.system.controller.vo.UserVO;
import com.yzm.system.entity.User;
import com.yzm.system.entity.UserRole;
import com.yzm.system.mapper.UserMapper;
import com.yzm.system.mapper.UserRoleMapper;
import com.yzm.system.query.UserQuery;
import com.yzm.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:27 2019/12/14
 * ===========================
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userDO = userMapper.findByUsername(username);
        if (null == userDO) {
            throw new UsernameNotFoundException(ResultEnum.UNKNOWN_ACCOUNT.getMessage());
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        userDO.getRoles().forEach(role -> {
            role.getMenus().forEach(menu -> {
                if (StringUtils.isNotBlank(menu.getPermission())) {
                    authorities.add(new SimpleGrantedAuthority(menu.getPermission()));
                }
            });
        });
        userDO.setAuthorities(authorities);
        return userDO;
    }

    @Override
    public PageVO<User> queryList(UserQuery query) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getAccount())) {
            wrapper.like("account", query.getAccount());
        }
        if (StringUtils.isNotBlank(query.getEmail())) {
            wrapper.like("email", query.getEmail());
        }
        if (StringUtils.isNotBlank(query.getPhone())) {
            wrapper.like("phone", query.getPhone());
        }
        if (query.getSex() != null) {
            wrapper.eq("sex", query.getSex());
        }
        if (null != query.getDeptId()) {
            wrapper.eq("dept_id", query.getDeptId());
        }
        wrapper.in("status", CoreConstant.ENABLE, CoreConstant.DISABLE);
        Page<User> page = new Page<>(query.getCurrent(), query.getSize());
        Page<User> userPage = userMapper.selectPage(page, wrapper);
        List<UserVO> userVOS = CopyUtil.copyList(userPage.getRecords(), UserVO.class);
        return PageVO.build(userPage.getTotal(), userVOS);
    }

    @Override
    public UserVO findById(Long id) {
        User ycUser = userMapper.selectById(id);
        UserVO userVO = CopyUtil.copyProperties(ycUser, UserVO.class);
        userVO.setRoles(userMapper.getRoleIds(userVO.getId()));
        return userVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer edit(UserVO userVO) {
        User user = CopyUtil.copyProperties(userVO, User.class);
        userRoleMapper.deleteUserRole(user.getId());
        this.insertUserRole(userVO);
        user.setUpdateTime(new Date());
        return userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(UserVO userVO) {
        User user = CopyUtil.copyProperties(userVO, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(DEFAULT_PASSWORD));
        user.setCreateTime(new Date());
        int insert = userMapper.insert(user);
        userVO.setId(user.getId());
        this.insertUserRole(userVO);
        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(UserVO userVO) {
        User user = CopyUtil.copyProperties(userVO, User.class);
        user.setStatus(CoreConstant.DELETE);
        return userMapper.updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertUserRole(UserVO userVO) {
        if (!CollectionUtils.isEmpty(userVO.getRoles())) {
            for (Long roleId : userVO.getRoles()) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userVO.getId());
                userRoleMapper.insert(userRole);
            }
        }
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
