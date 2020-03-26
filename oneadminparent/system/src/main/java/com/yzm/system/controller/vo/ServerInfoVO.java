package com.yzm.system.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * ==========================
 * 服务器信息
 *
 * @author : yizuomin
 * @date : Created in 21:33 2019/12/24
 * ===========================
 */
@Data
@AllArgsConstructor
public class ServerInfoVO {

    /**
     * 系统名
     */
    private String osName;

    /**
     * cpu负载
     */
    private String systemCpuLoad;

    /**
     * jvm线程负载
     */
    private String getProcessCpuLoad;

    /**
     * 总的物理内存
     */
    private Long totalMemorySize;

    /**
     * 剩余的物理内存
     */
    private Long freePhysicalMemorySize;

    /**
     * 已使用的物理内存
     */
    private Long usedMemory;

    private Integer redisKeyCount;

    @JsonFormat(pattern = "HH:mm:ss")
    private Date time;
}
