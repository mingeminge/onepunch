package com.yzm.system.security;

import com.yzm.common.constant.CoreConstant;
import com.yzm.common.enums.ResultEnum;
import com.yzm.common.util.JsonUtil;
import com.yzm.common.vo.ResultVO;
import com.yzm.common.util.RedisUtil;
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
public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Setter
    private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            String header = request.getHeader(CoreConstant.TOKEN_HEADER);
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
                response.getWriter().write(JsonUtil.objectToJson(ResultVO.ok(ResultEnum.OK)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
