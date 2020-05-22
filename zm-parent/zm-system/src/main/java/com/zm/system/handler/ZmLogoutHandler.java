package com.zm.system.handler;

import com.zm.common.contant.TokenConstant;
import com.zm.common.enums.ResultEnum;
import com.zm.common.result.R;
import com.zm.common.utils.JsonUtil;
import com.zm.common.utils.RedisUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 16:57 2019/12/18
 * ===========================
 */
@Slf4j
public class ZmLogoutHandler implements LogoutSuccessHandler {

    @Setter
    private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            String header = request.getHeader(TokenConstant.TOKEN_HEADER);
            boolean b = redisUtil.deleteKey(header);
            if (b) {
                log.info("删除key:{} 成功", header);
            } else {
                log.info("删除key:{} 失败", header);
            }
        } catch (Exception e) {
            log.error("删除key失败：{}", e.getMessage());
        } finally {
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JsonUtil.objectToJson(R.ok(ResultEnum.OK)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
