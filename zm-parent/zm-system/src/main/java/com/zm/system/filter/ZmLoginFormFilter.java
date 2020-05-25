package com.zm.system.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zm.common.contant.TokenConstant;
import com.zm.common.enums.ResultEnum;
import com.zm.common.result.R;
import com.zm.common.utils.JsonUtil;
import com.zm.system.pojo.SystemUser;
import com.zm.system.pojo.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.jms.Topic;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 12:44 2019/12/18
 * ===========================
 */
@Slf4j
public class ZmLoginFormFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JmsMessagingTemplate jmsMessagingTemplate;

    private final Topic topic;

    public ZmLoginFormFilter(AuthenticationManager authenticationManager, JmsMessagingTemplate jmsMessagingTemplate, Topic topic) {
        this.authenticationManager = authenticationManager;
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.topic = topic;
    }

    @Getter
    @Setter
    private SystemUser systemUser;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            systemUser = objectMapper.readValue(request.getInputStream(), SystemUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(systemUser.getUsername(), systemUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SystemUser user = (SystemUser) authResult.getPrincipal();
        String token = TokenConstant.TOKEN_PREFIX + UUID.randomUUID().toString();

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setToken(token);
        userInfo.setSystem(systemUser.getSystem());
        userInfo.setBrowser(systemUser.getBrowser());
        userInfo.setIp(request.getRemoteAddr());
        userInfo.setRoles(user.getRoles());
        userInfo.setPermissions(user.getPermissions());

        log.info("用户{}登录成功", user.getUsername());
        jmsMessagingTemplate.convertAndSend(this.topic, JsonUtil.objectToJson(userInfo));
        user.setToken(token);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtil.objectToJson(R.data(userInfo)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtil.objectToJson(R.error(ResultEnum.USERNAME_PASSWORD_ERROR)));
    }
}
