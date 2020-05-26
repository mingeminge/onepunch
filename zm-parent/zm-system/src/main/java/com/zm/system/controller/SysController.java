package com.zm.system.controller;

import com.zm.common.contant.TokenConstant;
import com.zm.common.result.R;
import com.zm.common.utils.JsonUtil;
import com.zm.common.utils.RedisUtil;
import com.zm.system.pojo.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/26 13:52
 * ==========================
 **/
@RestController
@AllArgsConstructor
public class SysController {

    private final RedisUtil redisUtil;

    @GetMapping("/info")
    public R<UserInfo> info(HttpServletRequest request) {
        String header = request.getHeader(TokenConstant.TOKEN_HEADER);
        String json = redisUtil.get(header);
        UserInfo userInfo = JsonUtil.jsonToPojo(json, UserInfo.class);
        return R.data(userInfo);
    }
}
