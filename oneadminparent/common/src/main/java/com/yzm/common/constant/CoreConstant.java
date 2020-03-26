package com.yzm.common.constant;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 14:47 2019/12/17
 * ===========================
 */
public interface CoreConstant {

    /**
     * 正常
     */
    Integer ENABLE = 0;
    /**
     * 禁用
     */
    Integer DISABLE = 1;
    /**
     * 删除
     */
    Integer DELETE = 2;

    Long EXPIRE = 1800L;

    String TOKEN_HEADER = "Authorization";

    String TOKEN_PREFIX = "security:";

    Integer LOGIN_BG = 0;

    Integer LOGIN_GIF = 1;

    String KICK_OUT = "KICK_OUT";

}
