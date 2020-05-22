package com.zm.common.utils;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 11:05
 * ==========================
 **/
@Slf4j
@Component
public class SftpUtil {
    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.port}")
    private Integer port;

    @Value("${sftp.protocol}")
    private String protocol;

    @Value("${sftp.username}")
    private String username;

    @Value("${sftp.password}")
    private String password;

    @Value("${sftp.root}")
    private String root;

    @Value("${sftp.privateKey}")
    private String privateKey;

    @Value("${sftp.passphrase}")
    private String passphrase;

    @Value("${sftp.sessionStrictHostKeyChecking}")
    private String sessionStrictHostKeyChecking;

    @Value("${sftp.sessionConnectTimeout}")
    private Integer sessionConnectTimeout;

    @Value("${sftp.channelConnectedTimeout}")
    private Integer channelConnectedTimeout;

    private static final String SESSION_CONFIG_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";

    public boolean uploadFile(String targetPath, InputStream inputStream) throws Exception {
        ChannelSftp sftp = this.createSftp();
        try {
            sftp.cd(this.root);
            log.info("CD到目录： {}", this.root);


            int index = targetPath.lastIndexOf("/");
            String fileDir = targetPath.substring(0, index);
            String fileName = targetPath.substring(index + 1);
            boolean dirs = this.createDirs(fileDir, sftp);
            if (!dirs) {
                log.error("Remote path error. path:{}", targetPath);
                throw new Exception("Upload File failure");
            }
            sftp.put(inputStream, fileName);
            return true;
        } catch (Exception e) {
            log.error("Upload file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Upload File failure");
        } finally {
            this.disconnect(sftp);
        }
    }

    public File downloadFile(String targetPath) throws Exception {
        ChannelSftp sftp = this.createSftp();
        OutputStream outputStream = null;
        try {
            sftp.cd(this.root);
            log.info("Change path to {}", this.root);

            File file = new File(targetPath.substring(targetPath.lastIndexOf("/") + 1));

            outputStream = new FileOutputStream(file);
            sftp.get(targetPath, outputStream);
            log.info("Download file success. TargetPath: {}", targetPath);
            return file;
        } catch (Exception e) {
            log.error("Download file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Download File failure");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            this.disconnect(sftp);
        }
    }

    public boolean deleteFile(String targetPath) throws Exception {
        ChannelSftp sftp = null;
        try {
            sftp = this.createSftp();
            sftp.cd(this.root);
            sftp.rm(targetPath);
            return true;
        } catch (Exception e) {
            log.error("Delete file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Delete File failure");
        } finally {
            this.disconnect(sftp);
        }
    }

    private boolean createDirs(String dirPath, ChannelSftp sftp) {
        if (dirPath != null && !dirPath.isEmpty()
                && sftp != null) {
            String[] dirs = Arrays.stream(dirPath.split("/"))
                    .filter(StringUtils::isNotBlank)
                    .toArray(String[]::new);

            for (String dir : dirs) {
                try {
                    sftp.cd(dir);
                    log.info("Change directory {}", dir);
                } catch (Exception e) {
                    try {
                        sftp.mkdir(dir);
                        log.info("Create directory {}", dir);
                    } catch (SftpException e1) {
                        log.error("Create directory failure, directory:{}", dir, e1);
                        e1.printStackTrace();
                    }
                    try {
                        sftp.cd(dir);
                        log.info("Change directory {}", dir);
                    } catch (SftpException e1) {
                        log.error("Change directory failure, directory:{}", dir, e1);
                        e1.printStackTrace();
                    }
                }
            }
            return true;
        }
        return false;
    }

    private ChannelSftp createSftp() throws Exception {
        JSch jsch = new JSch();
        log.info("Try to connect sftp[" + this.username + "@" + this.host + "], use password[" + this.password + "]");

        Session session = createSession(jsch, this.host, this.username, this.port);
        session.setPassword(this.password);
        session.connect(this.sessionConnectTimeout);

        log.info("Session connected to {}.", this.host);

        Channel channel = session.openChannel(this.protocol);
        channel.connect(this.channelConnectedTimeout);

        log.info("Channel created to {}.", this.host);

        return (ChannelSftp) channel;
    }

    private ChannelSftp connectByKey() throws Exception {
        JSch jsch = new JSch();

        // 设置密钥和密码 ,支持密钥的方式登陆
        if (StringUtils.isNotBlank(this.privateKey)) {
            if (StringUtils.isNotBlank(this.passphrase)) {
                // 设置带口令的密钥
                jsch.addIdentity(this.privateKey, this.passphrase);
            } else {
                // 设置不带口令的密钥
                jsch.addIdentity(this.privateKey);
            }
        }
        log.info("Try to connect sftp[" + this.username + "@" + this.host + "], use private key[" + this.privateKey
                + "] with passphrase[" + this.passphrase + "]");

        Session session = createSession(jsch, this.host, this.username, this.port);
        // 设置登陆超时时间
        session.connect(this.sessionConnectTimeout);
        log.info("Session connected to " + this.host + ".");

        // 创建sftp通信通道
        Channel channel = session.openChannel(this.protocol);
        channel.connect(this.channelConnectedTimeout);
        log.info("Channel created to " + this.host + ".");
        return (ChannelSftp) channel;
    }

    private Session createSession(JSch jsch, String host, String username, Integer port) throws Exception {
        Session session = null;

        if (port <= 0) {
            session = jsch.getSession(username, host);
        } else {
            session = jsch.getSession(username, host, port);
        }

        if (session == null) {
            throw new Exception(host + " session is null");
        }

        session.setConfig(SESSION_CONFIG_STRICT_HOST_KEY_CHECKING, this.sessionStrictHostKeyChecking);
        return session;
    }

    private void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                } else if (sftp.isClosed()) {
                    log.info("sftp is closed already");
                }
                if (null != sftp.getSession()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
