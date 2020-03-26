package com.yzm.system.controller;

import com.wf.captcha.ArithmeticCaptcha;
import com.yzm.common.enums.ResultEnum;
import com.yzm.common.vo.ResultVO;
import com.yzm.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 15:13 2019/12/16
 * ===========================
 */
@RestController
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;

    /*@PostMapping("/login")
    public ResultVO login(@RequestBody LoginUser userLogin) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> map = new HashMap();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userLogin.getAccount(), userLogin.getPassword());
        try {
            *//*if (subject.isAuthenticated()) {
                return ResultVO.ok("已登陆");
            }*//*
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            String sessionId = (String) subject.getSession().getId();
            map.put("sessionId", sessionId);
        } catch (ShiroException e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());
        }
        return ResultVO.ok(map);
    }*/

    @GetMapping("/notLogin")
    public ResultVO noLogin() {
        return ResultVO.error(ResultEnum.PLEASE_LOGIN);
    }

    @GetMapping("/noPerms")
    public ResultVO noPerms() {
        return ResultVO.error(ResultEnum.PERMISSION_DENIED);
    }

    @GetMapping("/kickout")
    public ResultVO kickout() {
        return ResultVO.error(ResultEnum.KICK_OUT);
    }

    @GetMapping("/code")
    public ResultVO getCaptcha() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 40);
        captcha.setLen(2);
        String value = captcha.text();
        String key = "captcha:" + UUID.randomUUID();
        redisUtil.setToken(key, value);
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", key);
        }};
        return ResultVO.ok(imgResult);
    }
}
