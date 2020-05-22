package com.zm.system.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zm.admin.dto.UserDTO;
import com.zm.common.contant.TokenConstant;
import com.zm.common.enums.ResultEnum;
import com.zm.common.result.R;
import com.zm.common.utils.CopyUtil;
import com.zm.common.utils.HttpClientUtil;
import com.zm.common.utils.JsonUtil;
import com.zm.common.utils.RedisUtil;
import com.zm.log.entity.SysLoginLog;
import com.zm.log.service.ISysLoginLogService;
import com.zm.system.pojo.SystemUser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
public class ZmLoginFormFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final RedisUtil redisUtil;

    private final ISysLoginLogService loginLogService;

    @Getter
    @Setter
    private SystemUser systemUser;

    public ZmLoginFormFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil, ISysLoginLogService loginLogService) {
        this.authenticationManager = authenticationManager;
        this.redisUtil = redisUtil;
        this.loginLogService = loginLogService;
    }

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
        UserDTO user = (UserDTO) authResult.getPrincipal();
        user.setPassword(null);
        String token = TokenConstant.TOKEN_PREFIX + UUID.randomUUID().toString();
        SystemUser returnUserInfo = new SystemUser();
        List<String> roleList = new ArrayList<>();
        List<String> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            roleList.add(role.getName());
        });
        user.getMenus().forEach(permission -> {
            authorities.add(permission.getPermission());
        });
        returnUserInfo.setToken(token);
        returnUserInfo.setUsername(user.getUsername());
        returnUserInfo.setRoles(roleList);
        returnUserInfo.setPermissions(authorities);
        returnUserInfo.setAvatar(user.getAvatar());


        SystemUser systemUser = this.getIpInfo(returnUserInfo, request.getRemoteAddr());
        String value = JsonUtil.objectToJson(systemUser);
        redisUtil.setToken(token, value);
        log.info("用户{}登录成功", user.getUsername());

        this.saveLoginLog(systemUser);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtil.objectToJson(R.data(returnUserInfo)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtil.objectToJson(R.error(ResultEnum.USERNAME_PASSWORD_ERROR)));
    }

    private SystemUser getIpInfo(SystemUser systemUser, String ip) {
        systemUser.setIp(ip);
        systemUser.setSystem(systemUser.getSystem());
        systemUser.setBrowser(systemUser.getBrowser());
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
            systemUser.setAddress(country + "|" + region + "|" + city);
            systemUser.setIsp(isp);
            return systemUser;
        } catch (Exception e) {
            log.error("ip接口异常，原因：{}", e.getMessage());
        }
        return systemUser;
    }

    private void saveLoginLog(SystemUser systemUser) {
        SysLoginLog sysLoginLog = CopyUtil.copyProperties(systemUser, SysLoginLog.class);
        sysLoginLog.setLoginDate(new Date());
        loginLogService.save(sysLoginLog);
    }
}
