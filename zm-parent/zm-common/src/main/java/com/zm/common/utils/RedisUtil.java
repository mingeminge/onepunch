package com.zm.common.utils;

import com.zm.common.contant.TokenConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 11:16
 * ==========================
 **/
@Slf4j
@Component
public class RedisUtil {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void flushToken(String token) {
        stringRedisTemplate.expire(token, TokenConstant.EXPIRE, TimeUnit.SECONDS);
        log.info("{}->已刷新过期时间", token);
    }

    public void setToken(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, TokenConstant.EXPIRE, TimeUnit.SECONDS);
        log.info("{}->已添加进redis缓存", key);
    }

    public String get(String key) {
        log.info("查询->{}", key);
        return stringRedisTemplate.opsForValue().get(key);
    }

    public boolean hasKey(String key) {
        if (key != null) {
            String s = stringRedisTemplate.opsForValue().get(key);
            return StringUtils.isNotEmpty(s);
        }
        return false;
    }

    public boolean deleteKey(String key) {
        if (hasKey(key)) {
            stringRedisTemplate.delete(key);
            log.info("删除token->{}", key);
            return true;
        }
        return false;
    }
}
