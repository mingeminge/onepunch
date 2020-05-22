package com.yzm.system.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzm.common.constant.CoreConstant;
import com.yzm.common.enums.ResultEnum;
import com.yzm.common.util.CopyUtil;
import com.yzm.common.util.HttpClientUtil;
import com.yzm.common.util.JsonUtil;
import com.yzm.common.vo.ResultVO;
import com.yzm.system.entity.LoginLog;
import com.yzm.system.entity.User;
import com.yzm.system.service.LoginLogService;
import com.yzm.common.util.RedisUtil;
import com.yzm.system.controller.vo.LoginUser;
import com.yzm.system.controller.vo.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 12:44 2019/12/18
 * ===========================
 */
@Slf4j
public class CustomLoginFormFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final RedisUtil redisUtil;

    private final LoginLogService loginLogService;

    @Setter
    @Getter
    private LoginUser loginUser;

    public CustomLoginFormFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil, LoginLogService loginLogService) {
        this.authenticationManager = authenticationManager;
        this.redisUtil = redisUtil;
        this.loginLogService = loginLogService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.loginUser = objectMapper.readValue(request.getInputStream(), LoginUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        user.setPassword(null);
        String token = CoreConstant.TOKEN_PREFIX + UUID.randomUUID().toString();
        UserInfo returnUserInfo = new UserInfo();
        List<String> roleList = new ArrayList<>();
        List<String> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            roleList.add(role.getName());
            role.getMenus().forEach(authority -> {
                if(StringUtils.isNotBlank(authority.getPermission())){
                    authorities.add(authority.getPermission());
                }
            });
        });
        returnUserInfo.setToken(token);
        returnUserInfo.setUsername(user.getUsername());
        returnUserInfo.setRoles(roleList);
        returnUserInfo.setAuthorities(authorities);
        returnUserInfo.setAvatar(user.getAvatar());

        LoginLog lastLogin = loginLogService.getLastLogin(returnUserInfo.getUsername());
        if(null!=lastLogin){
            returnUserInfo.setLastLoginTime(lastLogin.getLoginTime());
            returnUserInfo.setBrowser(lastLogin.getBrowser());
            returnUserInfo.setAddress(lastLogin.getAddress());
            returnUserInfo.setSystem(lastLogin.getSystem());
            returnUserInfo.setIp(lastLogin.getIp());
            returnUserInfo.setIsp(lastLogin.getIsp());
        }

        UserInfo ipInfo = getIpInfo(returnUserInfo, request.getRemoteAddr());

        String value = JsonUtil.objectToJson(ipInfo);
        redisUtil.setToken(token, value);
        log.info("用户{}登录成功", user.getUsername());

        LoginLog loginLog = CopyUtil.copyProperties(ipInfo, LoginLog.class);
        loginLog.setLoginTime(new Date());
        loginLogService.save(loginLog);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtil.objectToJson(ResultVO.ok(returnUserInfo)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtil.objectToJson(ResultVO.error(ResultEnum.USERNAME_PASSWORD_ERROR)));
    }

    private UserInfo getIpInfo(UserInfo userInfo, String ip) {
        userInfo.setIp(ip);
        userInfo.setSystem(loginUser.getSystem());
        userInfo.setBrowser(loginUser.getBrowser());
        Map<String, String> map = new HashMap<>(2);
        String path = "https://way.jd.com/Quxun/ipaddress";
        map.put("ipaddress", ip);
        map.put("appkey", "278782cf0c1a0442af27ed86d297ca03");
        try {
            String resp = HttpClientUtil.doGet(path, map);
            JSONObject jsonObject = JSON.parseObject(resp);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject data = result.getJSONObject("data");
            String city = data.getString("address_city");
            String region = data.getString("address_province");
            String isp = data.getString("address_isp");
            String country = data.getString("address_country");
            userInfo.setAddress(country + "|" + region + "|" + city);
            userInfo.setIsp(isp);
            return userInfo;
        } catch (Exception e) {
            log.error("ip接口异常，原因：{}",e.getMessage());
        }
        return userInfo;
    }
}
