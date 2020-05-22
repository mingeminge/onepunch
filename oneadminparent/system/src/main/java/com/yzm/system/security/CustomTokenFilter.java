package com.yzm.system.security;

import com.yzm.common.constant.CoreConstant;
import com.yzm.common.enums.ResultEnum;
import com.yzm.common.util.JsonUtil;
import com.yzm.common.vo.ResultVO;
import com.yzm.common.util.RedisUtil;
import com.yzm.system.controller.vo.UserInfo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author : yizuomin
 * @date : Created in 16:41 2019/4/11
 */

public class CustomTokenFilter extends BasicAuthenticationFilter {

    private final RedisUtil redisUtil;

    private static final String ROLE_LOGIN = "ROLE_LOGIN";

    public CustomTokenFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil) {
        super(authenticationManager);
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(CoreConstant.TOKEN_HEADER);
        response.setContentType("application/json;charset=UTF-8");
        if (token == null) {
            response.getWriter().write(JsonUtil.objectToJson(ResultVO.error(ResultEnum.PLEASE_LOGIN)));
            return;
        }
        if (!token.startsWith(CoreConstant.TOKEN_PREFIX)) {
            response.getWriter().write(JsonUtil.objectToJson(ResultVO.error(ResultEnum.PLEASE_LOGIN)));
            return;
        }
        if (!redisUtil.hasKey(token)) {
            response.getWriter().write(JsonUtil.objectToJson(ResultVO.error(ResultEnum.TIME_OUT)));
            return;
        }

        if (redisUtil.hasKey(token) && token.startsWith(CoreConstant.TOKEN_PREFIX)) {

            String userJon = redisUtil.get(token);
            UserInfo user = JsonUtil.jsonToPojo(userJon, UserInfo.class);
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            UsernamePasswordAuthenticationToken authenticationToken = null;
            if (null != user && user.getAuthorities() != null) {
                user.getAuthorities().forEach(authority -> {
                    authorities.add(new SimpleGrantedAuthority(authority));
                });

                authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
                redisUtil.flushToken(token);
            } else if (user != null) {
                    authorities.add(new SimpleGrantedAuthority(ROLE_LOGIN));
                    authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
                }
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
