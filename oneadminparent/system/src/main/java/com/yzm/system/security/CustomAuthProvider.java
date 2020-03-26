package com.yzm.system.security;

import com.yzm.common.enums.ResultEnum;
import com.yzm.common.util.Base64Util;
import com.yzm.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * ==========================
 * <p>
 * 自定义登陆实现
 *
 * @author : yizuomin
 * @date : Created in 12:29 2019/12/18
 * ===========================
 */
@Slf4j
@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private UserService userService;

    @Autowired
    public CustomAuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String pwd = Base64Util.getFromBase64(password);

        UserDetails userInfo = userService.loadUserByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException(ResultEnum.UNKNOWN_ACCOUNT.getMessage());
        }
        /*if (!pwd.equals(userInfo.getPassword())) {
            throw new BadCredentialsException(ResultEnum.INCORRECT_CREDENTIALS.getMessage());
        }*/

        if (!new BCryptPasswordEncoder().matches(pwd, userInfo.getPassword())) {
            log.error("用户{},登录失败，原因：密码不正确", username);
            throw new BadCredentialsException("密码错误");
        }

        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
