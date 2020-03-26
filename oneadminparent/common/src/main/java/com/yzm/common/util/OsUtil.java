package com.yzm.common.util;


import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;


/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 23:38 2019/12/23
 * ===========================
 */

public class OsUtil {

    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    /**
     * 获取操作系统名称
     *
     * @return
     */
    public static String getOsName() {
        // 操作系统
        String osName = System.getProperty("os.name");
        return osName;
    }

    /**
     * 获取系统cpu负载
     *
     * @return
     */
    public static double getSystemCpuLoad() {
        double SystemCpuLoad = osmxb.getSystemCpuLoad();
        return SystemCpuLoad;
    }

    /**
     * 获取jvm线程负载
     *
     * @return
     */
    public static String getProcessCpuLoad() {
        double ProcessCpuLoad = osmxb.getProcessCpuLoad();
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(ProcessCpuLoad);
    }

    /**
     * 获取总的物理内存
     *
     * @return
     */
    public static long getTotalMemorySize() {
        int MB = 1024 * 1024;
        // 总的物理内存
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / MB;
        return totalMemorySize;
    }

    /**
     * 获取剩余的物理内存
     *
     * @return
     */
    public static long getFreePhysicalMemorySize() {
        int MB = 1024 * 1024;
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / MB;
        return freePhysicalMemorySize;
    }

    /**
     * 获取已使用的物理内存
     *
     * @return
     */
    public static long getUsedMemory() {
        int MB = 1024 * 1024;
        //已使用的物理内存
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / MB;
        return usedMemory;
    }

    public static void main(String[] args) {
        String osName = getOsName();
        System.out.println(osName);

        double systemCpuLoad = getSystemCpuLoad();
        System.out.println(systemCpuLoad);

        String processCpuLoad = getProcessCpuLoad();
        System.out.println(processCpuLoad);
        long totalMemorySize = getTotalMemorySize();
        long freePhysicalMemorySize = getFreePhysicalMemorySize();
        long usedMemory = getUsedMemory();

        System.out.println(totalMemorySize);
        System.out.println(freePhysicalMemorySize);
        System.out.println(usedMemory);
    }
}
