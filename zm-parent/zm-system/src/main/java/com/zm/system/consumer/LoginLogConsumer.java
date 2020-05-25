package com.zm.system.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zm.common.utils.CopyUtil;
import com.zm.common.utils.HttpClientUtil;
import com.zm.common.utils.JsonUtil;
import com.zm.common.utils.RedisUtil;
import com.zm.log.entity.SysLoginLog;
import com.zm.log.service.ISysLoginLogService;
import com.zm.system.pojo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/25 10:09
 * ==========================
 **/
@Slf4j
@Component
public class LoginLogConsumer {

    private final ISysLoginLogService loginLogService;

    private final RedisUtil redisUtil;

    public LoginLogConsumer(ISysLoginLogService loginLogService, RedisUtil redisUtil) {
        this.loginLogService = loginLogService;
        this.redisUtil = redisUtil;
    }

    @JmsListener(destination = "${spring.activemq.login-log}", containerFactory = "topicListener")
    public void readActiveQueue(String message) {
        System.out.println("topic接受到：" + message);
        UserInfo userInfo = JsonUtil.jsonToPojo(message, UserInfo.class);
        if (null != userInfo) {
            SysLoginLog loginLog = CopyUtil.copyProperties(userInfo, SysLoginLog.class);
            UserInfo ipInfo = this.getIpInfo(userInfo);
            if (null != ipInfo) {
                userInfo.setIsp(ipInfo.getIsp());
                userInfo.setAddress(ipInfo.getAddress());
                redisUtil.setToken(userInfo.getToken(), JsonUtil.objectToJson(userInfo));

                loginLog.setAddress(ipInfo.getAddress());
                loginLog.setIp(userInfo.getIp());
                loginLog.setIsp(userInfo.getIsp());
                loginLog.setLoginDate(new Date());
                loginLogService.save(loginLog);
            }
        }
    }

    private UserInfo getIpInfo(UserInfo userInfo) {
        String path = "https://way.jd.com/Quxun/ipaddress?appkey=278782cf0c1a0442af27ed86d297ca03&ipaddress=" + "120.197.17.187";
        try {
            String resp = HttpClientUtil.doGet(path);
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
            log.error("ip接口异常，原因：{}", e.getMessage());
        }
        return null;
    }
}
