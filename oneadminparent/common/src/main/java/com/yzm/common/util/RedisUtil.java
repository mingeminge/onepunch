package com.yzm.common.util;

import com.yzm.common.constant.CoreConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author : yizuomin
 * @date : Created in 09:55 2019/3/25
 */
@Slf4j
@Component
public class RedisUtil {


    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void flushToken(String token) {
        stringRedisTemplate.expire(token, CoreConstant.EXPIRE, TimeUnit.SECONDS);
        log.info("{}->已刷新过期时间", token);
    }

    public void setToken(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, CoreConstant.EXPIRE, TimeUnit.SECONDS);
        log.info("{}->已添加进redis缓存", key);
    }

    public boolean hasKey(String key) {
        if (key != null) {
            String s = stringRedisTemplate.opsForValue().get(key);
            return StringUtils.isNotEmpty(s);
        }
        return false;
    }

    public boolean isExpire(String key) {
        Long expire = stringRedisTemplate.getExpire(key);
        return expire != null && expire > 0;
    }

    public boolean deleteKey(String key) {
        if (hasKey(key)) {
            stringRedisTemplate.delete(key);
            log.info("删除token->{}", key);
            return true;
        }
        return false;
    }

    public String get(String key) {
        log.info("查询->{}", key);
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Set<String> keys(String key) {
        return stringRedisTemplate.keys(key);
    }
}
