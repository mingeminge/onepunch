package com.yzm.common.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 15:02 2019/12/6
 * ===========================
 */
@Data
@Component
@ConfigurationProperties(ignoreInvalidFields = false, prefix = "sftp.client")
public class SftpProperties {
    private String host;

    private Integer port;

    private String protocol;

    private String username;

    private String password;

    private String root;

    private String privateKey;

    private String passphrase;

    private String sessionStrictHostKeyChecking;

    private Integer sessionConnectTimeout;

    private Integer channelConnectedTimeout;
}
